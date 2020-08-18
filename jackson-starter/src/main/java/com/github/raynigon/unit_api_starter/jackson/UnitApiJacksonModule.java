package com.github.raynigon.unit_api_starter.jackson;

import com.fasterxml.jackson.core.Version;
import com.github.raynigon.unit_api_starter.jackson.deserializers.UnitApiDeserializers;
import com.github.raynigon.unit_api_starter.jackson.serializers.UnitApiSerializers;

public class UnitApiJacksonModule extends com.fasterxml.jackson.databind.Module {

    // TODO dynamically determine Version Informations
    private static final Version VERSION = new Version(0, 0, 1, null, "", "");

    @Override
    public String getModuleName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Version version() {
        return VERSION;
    }

    @Override
    public void setupModule(SetupContext context) {
        // Serializers
        context.addSerializers(new UnitApiSerializers());

        // Deserializers
        context.addDeserializers(new UnitApiDeserializers());
    }
}
