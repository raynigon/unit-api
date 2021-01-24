package com.raynigon.unit_api.core.deserializer;

import javax.measure.Quantity;

public interface QuantityStringDeserializer {
    Quantity<?> deserialize(String input);
}
