package com.raynigon.unit_api.jackson.deserializer.extractor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.raynigon.unit_api.core.service.UnitsApiService;
import com.raynigon.unit_api.jackson.exception.MissingUnitException;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class NumberExtractor {

    public final static List<Integer> HANDLED_TOKENS = Arrays.asList(JsonTokenId.ID_NUMBER_INT, JsonTokenId.ID_NUMBER_FLOAT);

    public static Number extract(JsonParser parser, DeserializationContext context) throws IOException {
        if (parser.getCurrentTokenId() == JsonTokenId.ID_NUMBER_INT) {
            return parser.getLongValue();
        }
        return parser.getDoubleValue();
    }
}
