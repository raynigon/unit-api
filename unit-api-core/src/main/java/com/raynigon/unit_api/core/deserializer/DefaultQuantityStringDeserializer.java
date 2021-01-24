package com.raynigon.unit_api.core.deserializer;

import com.raynigon.unit_api.core.service.UnitsApiService;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultQuantityStringDeserializer implements QuantityStringDeserializer {

    private static final Pattern PATTERN = Pattern.compile("^(-?[.0-9]+\\.?[0-9]*)\\s*([A-z²³\\/\\\\%]+)$");

    @Override
    public Quantity<?> deserialize(String input) {
        if (input == null) return null;
        if (input.isEmpty()) return null;

        Matcher matcher = PATTERN.matcher(input);
        if (!matcher.matches()) return null;
        String valueGroup = matcher.group(1);
        String unitGroup = matcher.group(2);

        Number value = Double.parseDouble(valueGroup);
        Unit<?> unit = UnitsApiService.getInstance().getUnit(unitGroup);

        return UnitsApiService.getInstance().createQuantity(value, unit);
    }
}
