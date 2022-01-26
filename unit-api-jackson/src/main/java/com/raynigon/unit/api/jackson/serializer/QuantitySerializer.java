package com.raynigon.unit.api.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.raynigon.unit.api.core.annotation.QuantityShape;
import com.raynigon.unit.api.core.exception.UnitNotFoundException;
import com.raynigon.unit.api.core.io.QuantityWriter;
import com.raynigon.unit.api.core.service.UnitsApiService;
import com.raynigon.unit.api.core.units.general.IUnit;
import com.raynigon.unit.api.jackson.annotation.JsonQuantityWriter;
import com.raynigon.unit.api.jackson.annotation.JsonUnit;
import com.raynigon.unit.api.jackson.config.UnitApiConfig;
import com.raynigon.unit.api.jackson.exception.IncompatibleUnitException;
import com.raynigon.unit.api.jackson.exception.UnknownUnitException;
import com.raynigon.unit.api.jackson.annotation.JsonQuantityHelper;
import com.raynigon.unit.api.jackson.annotation.JsonUnitHelper;
import lombok.extern.slf4j.Slf4j;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@SuppressWarnings("rawtypes")
public class QuantitySerializer extends JsonSerializer<Quantity> implements ContextualSerializer {

    private final UnitApiConfig config;
    private final Unit<?> unit;
    private final QuantityShape shape;
    private final QuantityWriter writer;

    public QuantitySerializer(UnitApiConfig config) {
        this(config, null, QuantityShape.NUMBER, UnitsApiService.writer());
    }

    public QuantitySerializer(UnitApiConfig config, Unit<?> unit, QuantityShape shape, QuantityWriter writer) {
        Objects.requireNonNull(config);
        this.config = config;
        this.unit = unit;
        this.shape = shape;
        this.writer = writer;
        log.trace("Created QuantitySerializer with config={}, unit={}, shape={}, writer={}", config, unit, shape, writer);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
            throws JsonMappingException {
        UnitApiConfig config = this.config;
        Unit<?> unit = getSystemUnit(prov, property);
        QuantityShape shape = this.shape;
        QuantityWriter writer = this.writer;
        log.trace("Property {} has system unit {}", property.getName(), unit);

        JsonUnit unitWrapper = property.getAnnotation(JsonUnit.class);
        if (unitWrapper == null) return new QuantitySerializer(config, unit, shape, writer);
        shape = JsonUnitHelper.getShape(unitWrapper);
        unit = getAnnotatedUnit(prov, property, unit, unitWrapper);

        JsonQuantityWriter writerWrapper = property.getAnnotation(JsonQuantityWriter.class);
        if (writerWrapper != null) {
            writer = JsonQuantityHelper.getWriterInstance(writerWrapper);
            shape = QuantityShape.STRING;
        }
        log.trace("Property {} is using custom quantity writer {}", property.getName(), writer);

        return new QuantitySerializer(config, unit, shape, writer);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void serialize(Quantity quantity, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        log.debug("Prepare {} with unit={} shape={} for write to {}", quantity, unit, shape, gen.getOutputContext());
        Quantity convertedQuantity = quantity;
        if (this.unit != null) {
            convertedQuantity = quantity.to(unit);
        }
        switch (shape) {
            case NUMBER:
                gen.writeNumber(convertedQuantity.getValue().doubleValue());
                break;
            case NUMERIC_STRING:
                gen.writeString("" + convertedQuantity.getValue().doubleValue());
                break;
            case STRING:
                gen.writeString(writer.write(convertedQuantity));
                break;
            case OBJECT:
                gen.writeStartObject();
                gen.writeFieldName("value");
                gen.writeNumber(convertedQuantity.getValue().doubleValue());
                gen.writeFieldName("unit");
                gen.writeString(convertedQuantity.getUnit().getSymbol());
                gen.writeEndObject();
                break;
            default:
                throw new IllegalArgumentException("Unknown Shape: " + shape);
        }
    }

    private Unit<?> getAnnotatedUnit(SerializerProvider prov, BeanProperty property, Unit<?> systemUnit, JsonUnit unitWrapper) throws IncompatibleUnitException {
        IUnit<?> annotatedUnit = JsonUnitHelper.getUnitInstance(unitWrapper);
        if (annotatedUnit == null) {
            return systemUnit;
        }
        log.trace("Property {} is using annotated unit {}", property.getName(), systemUnit);
        if (!systemUnit.isCompatible(annotatedUnit)) {
            throw new IncompatibleUnitException(prov.getGenerator(), systemUnit, annotatedUnit);
        }
        return annotatedUnit;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Unit<?> getSystemUnit(SerializerProvider prov, BeanProperty property) throws UnknownUnitException {
        Unit<?> unit;
        Class<Quantity> quantityType = (Class<Quantity>) property.getType()
                .getBindings()
                .getBoundType(0)
                .getRawClass();
        try {
            return UnitsApiService.getInstance().getUnit(quantityType);
        } catch (UnitNotFoundException exception) {
            throw new UnknownUnitException(prov.getGenerator(), quantityType);
        }
    }
}
