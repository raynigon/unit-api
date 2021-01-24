package com.raynigon.unit_api.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.raynigon.unit_api.core.service.UnitsApiService;
import com.raynigon.unit_api.jackson.exception.MissingUnitException;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.io.IOException;

public class QuantityNumberDeserializer implements QuantitySubDeserializer {

    private final Unit<?> unit;

    public QuantityNumberDeserializer(Unit<?> unit) {
        this.unit = unit;
    }

    @Override
    public boolean canDeserialize(JsonParser parser, DeserializationContext context) {
        return (parser.getCurrentTokenId() == JsonTokenId.ID_NUMBER_INT ||
                parser.getCurrentTokenId() == JsonTokenId.ID_NUMBER_FLOAT
        );
    }

    @Override
    public Quantity<?> deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        if (unit == null) {
            throw new MissingUnitException(parser, "?");
        }
        if (parser.getCurrentTokenId() == JsonTokenId.ID_NUMBER_INT) {
            UnitsApiService.getInstance().createQuantity(parser.getLongValue(), unit);
        }
        return UnitsApiService.getInstance().createQuantity(parser.getDoubleValue(), unit);
    }
}
