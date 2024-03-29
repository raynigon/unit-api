package com.raynigon.unit.api.core.io;

import com.raynigon.unit.api.core.service.UnitsApiService;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultQuantityReader implements QuantityReader {

    private static final Pattern PATTERN = Pattern.compile("^(-?[.0-9]+\\.?[0-9]*)\\s*(.+)$");

    @Override
    public Quantity<?> read(String input) {
        if (input == null) return null;
        if (input.isEmpty()) return null;

        Matcher matcher = PATTERN.matcher(input);
        if (!matcher.matches()) return null;
        String valueGroup = matcher.group(1);
        String unitGroup = matcher.group(2);

        Number value = Double.parseDouble(valueGroup);
        Unit<?> unit = UnitsApiService.getInstance().getUnit(unitGroup);
        if (unit == null) return null;

        return UnitsApiService.getInstance().createQuantity(value, unit);
    }
}
