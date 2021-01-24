package com.raynigon.unit_api.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.raynigon.unit_api.core.service.UnitsApiService;
import com.raynigon.unit_api.jackson.exception.UnknownUnitException;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.io.IOException;

public class QuantityObjectDeserializer implements QuantitySubDeserializer {

    private final Unit<?> unit;
    private final boolean forceUnit;

    public QuantityObjectDeserializer(Unit<?> unit, boolean forceUnit) {
        this.unit = unit;
        this.forceUnit = forceUnit;
    }

    @Override
    public boolean canDeserialize(JsonParser parser, DeserializationContext context) {
        return (parser.getCurrentTokenId() == JsonTokenId.ID_START_OBJECT);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Quantity<?> deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        int tokenId = parser.nextToken().id();
        DeserializationState state = DeserializationState.UNKNOWN;

        double valueField = 0.0;
        Unit<?> unitField = null;
        boolean valueParsed = false;

        while (tokenId != JsonTokenId.ID_END_OBJECT) {
            switch (tokenId) {
                case JsonTokenId.ID_FIELD_NAME:
                    if ("value".equalsIgnoreCase(parser.getValueAsString())) {
                        state = DeserializationState.VALUE_FIELD;
                    } else if ("unit".equalsIgnoreCase(parser.getValueAsString())) {
                        state = DeserializationState.UNIT_FIELD;
                    } else {
                        throw new JsonMappingException(parser, "Unexpected Data");
                    }
                    break;
                case JsonTokenId.ID_NUMBER_FLOAT:
                case JsonTokenId.ID_NUMBER_INT:
                    if (state == DeserializationState.VALUE_FIELD) {
                        valueField = parser.getDoubleValue();
                        valueParsed = true;
                    } else {
                        throw new JsonMappingException(parser, "Unexpected Data");
                    }
                    state = DeserializationState.UNKNOWN;
                    break;
                case JsonTokenId.ID_STRING:
                    if (state == DeserializationState.UNIT_FIELD) {
                        unitField = parseUnit(parser.getValueAsString());
                        if (unitField == null) {
                            throw new UnknownUnitException(parser, parser.getValueAsString());
                        }
                    } else {
                        throw new JsonMappingException(parser, "Unexpected Data");
                    }
                    state = DeserializationState.UNKNOWN;
                    break;
                default:
                    throw new JsonMappingException(parser, "Unexpected Data");
            }
            tokenId = parser.nextToken().id();
        }
        if (!valueParsed) throw new JsonMappingException(parser, "Missing Value Field");
        Quantity<?> result = UnitsApiService.getInstance().createQuantity(valueField, unitField);
        return forceUnit ? result.to((Unit) this.unit) : result;
    }

    private Unit<?> parseUnit(String unitSymbol) {
        return UnitsApiService.getInstance().getUnit(unitSymbol);
    }

    private enum DeserializationState {
        UNKNOWN,
        VALUE_FIELD,
        UNIT_FIELD,
    }
}
