package com.raynigon.unit_api.core.io;

import javax.measure.Quantity;

public interface QuantityWriter {

    String write(Quantity<?> input);
}
