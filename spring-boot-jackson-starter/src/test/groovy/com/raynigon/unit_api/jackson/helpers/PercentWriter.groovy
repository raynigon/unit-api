package com.raynigon.unit_api.jackson.helpers

import com.raynigon.unit_api.core.io.QuantityWriter
import com.raynigon.unit_api.core.units.si.dimensionless.Percent

import javax.measure.Quantity

class PercentWriter implements QuantityWriter {
    @Override
    String write(Quantity input) {
        return "${input.to(new Percent()).value.doubleValue()}%"
    }
}
