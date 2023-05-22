package com.raynigon.unit.api.core;

import com.raynigon.unit.api.core.service.UnitsApiService;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.measure.spi.SystemOfUnits;
import java.util.List;

/**
 * The UnitApiCoreConfiguration provides the {@link UnitsApiService} as a bean
 */
@Configuration
@AutoConfiguration
public class UnitApiCoreConfiguration {

    private final List<SystemOfUnits> systemsOfUnits;

    /**
     * Creates a new configuration object which provides a {@link UnitsApiService} bean, which contains all given systems of units.
     *
     * @param systemsOfUnits The {@link SystemOfUnits} beans which should be usable from the {@link UnitsApiService}
     */
    public UnitApiCoreConfiguration(List<SystemOfUnits> systemsOfUnits) {
        this.systemsOfUnits = systemsOfUnits;
    }

    /**
     * Initialize the {@link UnitsApiService} bean with all existing {@link SystemOfUnits}
     */
    @PostConstruct
    public void init() {
        for (SystemOfUnits system : systemsOfUnits) {
            UnitsApiService.getInstance().addSystemOfUnits(system);
        }
    }

    /**
     * Bean factory for the {@link UnitsApiService}
     *
     * @return The {@link UnitsApiService} bean which is initialized with all {@link SystemOfUnits} beans
     */
    @Bean
    public UnitsApiService unitsApiService() {
        return UnitsApiService.getInstance();
    }
}
