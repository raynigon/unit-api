package com.raynigon.unit_api.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;

import javax.measure.Quantity;
import java.io.IOException;

public interface QuantitySubDeserializer {

    boolean canDeserialize(JsonParser parser, DeserializationContext context);

    Quantity<?> deserialize(JsonParser parser, DeserializationContext context) throws IOException;
}
