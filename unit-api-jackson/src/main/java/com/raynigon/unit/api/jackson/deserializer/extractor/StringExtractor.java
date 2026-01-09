package com.raynigon.unit.api.jackson.deserializer.extractor;


import org.apache.commons.lang3.StringUtils;
import tools.jackson.core.JsonTokenId;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class StringExtractor {

    public final static List<Integer> HANDLED_TOKENS = Collections.singletonList(JsonTokenId.ID_STRING);

    public static Object extract(JsonParser parser, DeserializationContext context) {
        String value = parser.getValueAsString();
        if (StringUtils.isNumeric(value)) {
            return Double.parseDouble(value);
        }
        return value;
    }
}
