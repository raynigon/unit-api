package com.raynigon.unit_api.jackson.helpers

import com.raynigon.unit_api.core.io.QuantityWriter

import javax.measure.Quantity

class DummyQuantityWriter implements QuantityWriter{
    @Override
    String write(Quantity<?> input) {
        return input.toString()
    }
}
