package com.raynigon.unit.api.jackson.deserializer.extractor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.raynigon.unit.api.jackson.exception.UnknownUnitException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ObjectExtractor {

    public final static List<Integer> HANDLED_TOKENS = Collections.singletonList(JsonTokenId.ID_START_OBJECT);

    public static String extract(JsonParser parser, DeserializationContext context) throws IOException {
        int tokenId = parser.nextToken().id();
        DeserializationState state = DeserializationState.UNKNOWN;

        double valueField = 0.0;
        String unitField = null;
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
                        unitField = parser.getValueAsString();
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
        if (unitField == null) throw new JsonMappingException(parser, "Missing Unit Field");
        return valueField + " " + unitField;
    }

    private enum DeserializationState {
        UNKNOWN,
        VALUE_FIELD,
        UNIT_FIELD,
    }
}
