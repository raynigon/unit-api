package com.raynigon.unit_api.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.raynigon.unit_api.core.annotation.QuantityShape;
import com.raynigon.unit_api.core.service.UnitsApiService;
import com.raynigon.unit_api.jackson.annotation.JsonUnit;
import com.raynigon.unit_api.jackson.annotation.JsonUnitHelper;
import com.raynigon.unit_api.jackson.exception.UnknownUnitException;

import java.io.IOException;
import javax.measure.Quantity;
import javax.measure.Unit;

@SuppressWarnings("rawtypes")
public class QuantitySerializer extends JsonSerializer<Quantity> implements ContextualSerializer {

    private Unit<?> unit;
    private QuantityShape shape = QuantityShape.NUMBER;

    public QuantitySerializer() {
    }

    public QuantitySerializer(Unit<?> unit, QuantityShape shape) {
        this.unit = unit;
        this.shape = shape;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
            throws JsonMappingException {
        Class<Quantity> quantityType =
                (Class<Quantity>) property.getType().getBindings().getBoundType(0).getRawClass();
        unit = UnitsApiService.getInstance().getUnit(quantityType);

        JsonUnit unitWrapper = property.getAnnotation(JsonUnit.class);
        if (unitWrapper == null) return new QuantitySerializer(unit, shape);
        shape = JsonUnitHelper.getShape(unitWrapper);
        String unitName = JsonUnitHelper.getUnitName(unitWrapper);

        if (unitName != null) {
            unit = UnitsApiService.getInstance().getUnit(unitName);
        }
        if (unit == null) {
            throw new UnknownUnitException(prov.getGenerator(), unitName);
        }

        return new QuantitySerializer(unit, shape);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void serialize(Quantity quantity, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        Quantity convertedQuantity = quantity;
        if (this.unit != null) {
            convertedQuantity = quantity.to(unit);
        }
        switch (shape) {
            case NUMBER:
                gen.writeNumber(convertedQuantity.getValue().doubleValue());
                break;
            case STRING:
                gen.writeString(UnitsApiService.getInstance().format(convertedQuantity));
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
}
