package com.raynigon.unit.api.jackson.deserializer.extractor;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonTokenId;
import tools.jackson.databind.DeserializationContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class NumberExtractor {

    public final static List<Integer> HANDLED_TOKENS = Arrays.asList(JsonTokenId.ID_NUMBER_INT, JsonTokenId.ID_NUMBER_FLOAT);

    public static Number extract(JsonParser parser, DeserializationContext context) {
        if (parser.currentTokenId() == JsonTokenId.ID_NUMBER_INT) {
            return parser.getLongValue();
        }
        return parser.getDoubleValue();
    }
}
