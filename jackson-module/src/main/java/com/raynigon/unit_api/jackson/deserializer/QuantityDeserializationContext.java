package com.raynigon.unit_api.jackson.deserializer;

import com.raynigon.unit_api.core.deserializer.DefaultQuantityStringDeserializer;
import com.raynigon.unit_api.core.deserializer.QuantityStringDeserializer;
import com.raynigon.unit_api.jackson.config.UnitApiConfig;

public class QuantityDeserializationContext {

    private final UnitApiConfig config;
    private final JacksonDeserializer[] deserializers;
    private final QuantityStringDeserializer stringDeserializer;
}
