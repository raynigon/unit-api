package com.raynigon.unit.api.jackson.deserializer.extractor;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonTokenId;
import tools.jackson.databind.DeserializationContext;
import com.raynigon.unit.api.jackson.exception.InvalidUnitException;
import tools.jackson.databind.exc.ValueInstantiationException;

import javax.measure.Quantity;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ObjectExtractor {

    public final static List<Integer> HANDLED_TOKENS = Collections.singletonList(JsonTokenId.ID_START_OBJECT);

    public static String extract(JsonParser parser, DeserializationContext context) {
        int tokenId = parser.nextToken().id();
        DeserializationState state = DeserializationState.UNKNOWN;

        double valueField = 0.0;
        String unitField = null;
        boolean valueParsed = false;

        while (tokenId != JsonTokenId.ID_END_OBJECT) {
            switch (tokenId) {
                case JsonTokenId.ID_PROPERTY_NAME:
                    if ("value".equalsIgnoreCase(parser.getValueAsString())) {
                        state = DeserializationState.VALUE_FIELD;
                    } else if ("unit".equalsIgnoreCase(parser.getValueAsString())) {
                        state = DeserializationState.UNIT_FIELD;
                    } else {
                        throw ValueInstantiationException.from(parser, "Unexpected Data", context.constructType(Quantity.class));
                    }
                    break;
                case JsonTokenId.ID_NUMBER_FLOAT:
                case JsonTokenId.ID_NUMBER_INT:
                    if (state == DeserializationState.VALUE_FIELD) {
                        valueField = parser.getDoubleValue();
                        valueParsed = true;
                    } else {
                        throw ValueInstantiationException.from(parser, "Unexpected Data");
                    }
                    state = DeserializationState.UNKNOWN;
                    break;
                case JsonTokenId.ID_STRING:
                    if (state == DeserializationState.UNIT_FIELD) {
                        unitField = parser.getValueAsString();
                        if (unitField == null) {
                            throw new InvalidUnitException(parser, parser.getValueAsString());
                        }
                    } else {
                        throw ValueInstantiationException.from(parser, "Unexpected Data", context.constructType(Quantity.class));
                    }
                    state = DeserializationState.UNKNOWN;
                    break;
                default:
                    throw ValueInstantiationException.from(parser, "Unexpected Data", context.constructType(Quantity.class));
            }
            tokenId = parser.nextToken().id();
        }
        if (!valueParsed) throw ValueInstantiationException.from(parser, "Missing Value Field", context.constructType(Quantity.class));
        if (unitField == null) throw ValueInstantiationException.from(parser, "Missing Unit Field", context.constructType(Quantity.class));
        return valueField + " " + unitField;
    }

    private enum DeserializationState {
        UNKNOWN,
        VALUE_FIELD,
        UNIT_FIELD,
    }
}
