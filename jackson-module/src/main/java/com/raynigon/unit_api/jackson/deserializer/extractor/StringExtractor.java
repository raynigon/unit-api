package com.raynigon.unit_api.jackson.deserializer.extractor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.raynigon.unit_api.core.service.UnitsApiService;
import com.raynigon.unit_api.jackson.exception.MissingUnitException;
import org.apache.commons.lang3.StringUtils;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StringExtractor {

    public final static List<Integer> HANDLED_TOKENS = Collections.singletonList(JsonTokenId.ID_STRING);

    public static Object extract(JsonParser parser, DeserializationContext context) throws IOException {
        String value = parser.getValueAsString();
        if (StringUtils.isNumeric(value)) {
            return Double.parseDouble(value);
        }
        return value;
    }
}
