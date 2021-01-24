package com.raynigon.unit_api.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.raynigon.unit_api.core.service.UnitsApiService;
import org.apache.commons.lang3.StringUtils;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.io.IOException;

public class QuantityStringDeserializer implements QuantitySubDeserializer {

    private final Unit<?> unit;
    private final boolean forceUnit;

    public QuantityStringDeserializer(Unit<?> unit, boolean forceUnit) {
        this.unit = unit;
        this.forceUnit = forceUnit;
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
            return UnitsApiService.getInstance().createQuantity(Double.parseDouble(value), unit);
        }
        Quantity<?> result = UnitsApiService.getInstance().parseQuantity(value);
        return forceUnit ? result.to((Unit) this.unit) : result;
    }

}
