package com.raynigon.unit_api.jackson;

import com.raynigon.unit_api.jackson.config.UnitApiFeature;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ConfigurationProperties(prefix="spring.jackson.unit-api")
public class UnitApiJacksonProperties {

    private Map<UnitApiFeature, Boolean> features = new EnumMap<>(UnitApiFeature.class);

    public void setFeatures(Map<UnitApiFeature, Boolean> features) {
        this.features = features;
    }

    public Map<UnitApiFeature, Boolean> getFeatures() {
        return features;
    }

    public Set<UnitApiFeature> getEnabledFeatures(){
        return features.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public Set<UnitApiFeature> getDisabledFeatures(){
        return features.entrySet().stream()
                .filter(it->!it.getValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
