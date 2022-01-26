package com.raynigon.unit.api.jackson.config;


public class UnitApiConfig {

    private final int featureMask;

    public UnitApiConfig(int featureMask){
        this.featureMask = featureMask;
    }

    public int getFeatureMask() {
        return featureMask;
    }

    public boolean isEnabled(UnitApiFeature feature) {
        return feature.enabledIn(featureMask);
    }
}
