package com.raynigon.unit.api.core.io;

import com.raynigon.unit.api.core.service.UnitsApiService;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultQuantityReader implements QuantityReader {

    @Override
    public Quantity<?> read(String input) {
        if (input == null) return null;
        if (input.isBlank()) return null;

        /*
        Split the input into value and unit.
        The value part ends either with a number or a dot.
        The unit part is everything else, trimmed.
         */
        int splitPoint = input.length() - 1;
        for (; splitPoint >= 0; splitPoint--) {
            char c = input.charAt(splitPoint);
            if (Character.isDigit(c) || c == '.') {
                break;
            }
        }
        splitPoint += 1;

        if (splitPoint > input.length()) return null;

        String valueGroup = input.substring(0, splitPoint);
        String unitGroup = input.substring(splitPoint).trim();

        Number value = Double.parseDouble(valueGroup);
        Unit<?> unit = UnitsApiService.getInstance().getUnit(unitGroup);
        if (unit == null) return null;

        return UnitsApiService.getInstance().createQuantity(value, unit);
    }
}
