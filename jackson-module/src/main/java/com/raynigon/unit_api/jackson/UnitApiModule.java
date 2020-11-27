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

    public UnitApiModule() {
        this(new UnitApiConfig(0));
    }

    public UnitApiModule(UnitApiConfig config) {
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

    public static Builder create() {
        return new Builder();
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
