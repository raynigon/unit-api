package com.raynigon.unit_api.core;

import com.raynigon.unit_api.core.service.UnitsApiService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.measure.spi.SystemOfUnits;
import java.util.List;

@Configuration
public class UnitApiCoreConfiguration {

    private final List<SystemOfUnits> systemsOfUnits;

    public UnitApiCoreConfiguration(List<SystemOfUnits> systemsOfUnits) {
        this.systemsOfUnits = systemsOfUnits;
    }

    @PostConstruct
    public void init() {
        for (SystemOfUnits system : systemsOfUnits) {
            UnitsApiService.getInstance().addSystemOfUnits(system);
        }
    }

    @Bean
    public UnitsApiService unitsApiService() {
        return UnitsApiService.getInstance();
    }
}
