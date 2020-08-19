package com.github.raynigon.unit_api_starter.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.github.raynigon.unit_api_starter.jackson.annotations.JsonUnit;
import com.github.raynigon.unit_api_starter.jackson.annotations.QuantityShape;
import tech.units.indriya.unit.Units;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.io.IOException;

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
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        Class<Quantity> quantityType = (Class<Quantity>) property.getType().getBindings().getBoundType(0).getRawClass();
        unit = Units.getInstance().getUnit(quantityType);

        JsonUnit unitWrapper = property.getAnnotation(JsonUnit.class);
        if (unitWrapper == null) return new QuantitySerializer(unit, shape);

        shape = unitWrapper.shape();

        String unitName = unitWrapper.unit();
        if (!"".equalsIgnoreCase(unitName)) unit = Units.getInstance().getUnit(unitName);

        return new QuantitySerializer(unit, shape);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void serialize(Quantity value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (this.unit!=null){
            value = value.to((Unit) unit);
        }
        switch (shape){
            case NUMBER:
                gen.writeNumber(value.getValue().doubleValue());
                break;
            case STRING:
                gen.writeString(value.toString());
                break;
            default:
                // TODO raise exception
        }
    }
}
