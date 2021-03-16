package com.raynigon.unit_api.core.io;

import javax.measure.Quantity;

public interface QuantityReader {

    Quantity<?> read(String input);
}
