package com.raynigon.unit.api.jackson.helpers

import com.raynigon.unit.api.core.io.QuantityWriter

import javax.measure.Quantity

class DummyQuantityWriter implements QuantityWriter{
    @Override
    String write(Quantity<?> input) {
        return input.toString()
    }
}
