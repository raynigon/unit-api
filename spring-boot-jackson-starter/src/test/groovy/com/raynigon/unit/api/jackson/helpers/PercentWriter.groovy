package com.raynigon.unit.api.jackson.helpers

import com.raynigon.unit.api.core.io.QuantityWriter
import com.raynigon.unit.api.core.units.si.dimensionless.Percent

import javax.measure.Quantity

class PercentWriter implements QuantityWriter {
    @Override
    String write(Quantity input) {
        return "${input.to(new Percent()).value.doubleValue()}%"
    }
}
