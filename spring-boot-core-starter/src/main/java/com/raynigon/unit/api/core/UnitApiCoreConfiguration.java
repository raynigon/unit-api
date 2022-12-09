package com.raynigon.unit.api.core;

import com.raynigon.unit.api.core.service.UnitsApiService;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.measure.spi.SystemOfUnits;
import java.util.List;

@Configuration
@AutoConfiguration
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
