package com.github.raynigon.unit_api_starter.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.raynigon.unit_api_starter.BuildVersion;
import com.github.raynigon.unit_api_starter.jackson.deserializers.QuantityDeserializer;
import com.github.raynigon.unit_api_starter.jackson.serializers.QuantitySerializer;

import javax.measure.Quantity;

public class UnitApiModule extends SimpleModule {

    private static final Version VERSION = new Version(
            BuildVersion.MAJOR_VERSION,
            BuildVersion.MINOR_VERSION,
            BuildVersion.PATCH_VERSION,
            BuildVersion.SNAPSHOT ? BuildVersion.BUILD_DATE.toString() : null,
            BuildVersion.GROUP_ID,
            BuildVersion.ARTIFACT_ID
    );

    public UnitApiModule(){
        addSerializer(Quantity.class, new QuantitySerializer());

        addDeserializer(Quantity.class, new QuantityDeserializer());
    }

    @Override
    public String getModuleName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Version version() {
        return VERSION;
    }
}
