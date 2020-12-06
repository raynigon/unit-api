package com.raynigon.unit_api.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.raynigon.unit_api.jackson.deserializer.QuantityDeserializer;
import com.raynigon.unit_api.jackson.config.UnitApiConfig;
import com.raynigon.unit_api.jackson.config.UnitApiFeature;
import com.raynigon.unit_api.jackson.serializer.QuantitySerializer;
import com.raynigon.unit_api.jackson_module.BuildVersion;

import javax.measure.Quantity;

public class UnitApiModule extends SimpleModule {

    private static final Version VERSION =
            new Version(
                    BuildVersion.MAJOR_VERSION,
                    BuildVersion.MINOR_VERSION,
                    BuildVersion.PATCH_VERSION,
                    BuildVersion.SNAPSHOT ? BuildVersion.BUILD_DATE.toString() : null,
                    BuildVersion.GROUP_ID,
                    BuildVersion.ARTIFACT_ID);

    private final UnitApiConfig config;

    public UnitApiModule() {
        this(new UnitApiConfig(0));
    }

    public UnitApiModule(UnitApiConfig config) {
        this.config = config;
        addSerializer(Quantity.class, new QuantitySerializer(config));
        addDeserializer(Quantity.class, new QuantityDeserializer(config));
    }

    @Override
    public String getModuleName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Version version() {
        return VERSION;
    }

    public UnitApiConfig getConfig() {
        return config;
    }

    public static Builder create() {
        return new Builder();
    }

    public static UnitApiModule withFeatures(UnitApiFeature... features) {
        return new Builder().enable(features).build();
    }

    public static UnitApiModule withoutFeatures(UnitApiFeature... features) {
        return new Builder().disable(features).build();
    }

    static class Builder {

        private int featureMask = 0;

        public Builder enable(UnitApiFeature... features) {
            for (UnitApiFeature feature : features){
                featureMask |= feature.getMask();
            }
            return this;
        }

        public Builder disable(UnitApiFeature... features) {
            for (UnitApiFeature feature : features){
                featureMask &= ~feature.getMask();
            }
            return this;
        }

        public UnitApiModule build() {
            return new UnitApiModule(new UnitApiConfig(featureMask));
        }
    }
}
