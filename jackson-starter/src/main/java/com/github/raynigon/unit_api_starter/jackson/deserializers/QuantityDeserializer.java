package com.github.raynigon.unit_api_starter.jackson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.ReferenceTypeDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.datatype.jdk8.BaseScalarOptionalDeserializer;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.measure.Quantity;
import javax.measure.spi.SystemOfUnitsService;
import java.io.IOException;
import java.util.Optional;
import java.util.OptionalInt;

public class QuantityDeserializer extends StdScalarDeserializer<Quantity<?>> {

    protected QuantityDeserializer(JavaType type) {
        super(type);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Quantity<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        switch (p.getCurrentTokenId()) {
            case JsonTokenId.ID_STRING:
                return Quantities.getQuantity(p.getValueAsString());
            case JsonTokenId.ID_NUMBER_INT:
            case JsonTokenId.ID_NUMBER_FLOAT:
                Class quantityType = getValueType().getBindings().getBoundType(0).getRawClass();
                return Quantities.getQuantity(
                        p.getValueAsDouble(),
                        Units.getInstance().getUnit(quantityType)
                );
            case JsonTokenId.ID_NULL:
            case JsonTokenId.ID_START_ARRAY:
                return null;
            default:
                return (Quantity<?>) ctxt.handleUnexpectedToken(_valueClass, p);
        }
    }
}
