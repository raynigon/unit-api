package com.raynigon.unit_api.jackson.helpers

import com.raynigon.unit_api.core.io.QuantityReader

import javax.measure.Quantity

import static com.raynigon.unit_api.core.units.si.SISystemUnitsConstants.Celsius

class CelsiusReader implements QuantityReader {

    @Override
    Quantity<?> read(String input) {
        if (!input.endsWith("°C")) return null
        return Celsius(Double.parseDouble(input.replace("°C", "").trim()))
    }
}
