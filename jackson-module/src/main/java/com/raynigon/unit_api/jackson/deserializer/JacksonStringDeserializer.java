package com.raynigon.unit_api.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.raynigon.unit_api.core.service.UnitsApiService;
import com.raynigon.unit_api.core.deserializer.QuantityStringDeserializer;
import com.raynigon.unit_api.jackson.exception.MissingUnitException;
import org.apache.commons.lang3.StringUtils;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.io.IOException;

public class JacksonStringDeserializer implements JacksonDeserializer {

    private final Unit<?> unit;
    private final boolean forceUnit;
    private final QuantityStringDeserializer deserializer;

    public JacksonStringDeserializer(Unit<?> unit, boolean forceUnit, QuantityStringDeserializer deserializer) {
        this.unit = unit;
        this.forceUnit = forceUnit;
        this.deserializer = deserializer;
    }

    @Override
    public boolean canDeserialize(JsonParser parser, DeserializationContext context) {
        return (parser.getCurrentTokenId() == JsonTokenId.ID_STRING);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Quantity<?> deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String value = parser.getValueAsString();
        if (StringUtils.isNumeric(value)) {
            return deserializeNumeric(parser, value);
        }
        Quantity<?> result = UnitsApiService.getInstance().parseQuantity(value);
        return forceUnit ? result.to((Unit) this.unit) : result;
    }

    private Quantity<?> deserializeNumeric(JsonParser parser, String value) throws MissingUnitException {
        if (unit == null) {
            throw new MissingUnitException(parser, "?");
        }
        return UnitsApiService.getInstance().createQuantity(Double.parseDouble(value), unit);
    }

}
