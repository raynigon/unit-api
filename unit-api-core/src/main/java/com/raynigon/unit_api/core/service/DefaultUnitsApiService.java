package com.raynigon.unit_api.core.service;

import com.raynigon.unit_api.core.exception.UnitNotFoundException;
import com.raynigon.unit_api.core.io.DefaultQuantityReader;
import com.raynigon.unit_api.core.quantities.NumberQuantity;
import com.raynigon.unit_api.core.units.si.SISystem;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.Set;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.spi.SystemOfUnits;

public class DefaultUnitsApiService implements UnitsApiService {

    private static final String DEFAULT_UNIT_API_SERVICE_AUTO_LOAD = "unitapi.service.autodiscovery";
    private static UnitsApiService INSTANCE = new DefaultUnitsApiService();

    private final SISystem baseSystem;

    private final Set<SystemOfUnits> systems;

    public static UnitsApiService getInstance() {
        return INSTANCE;
    }

    public static UnitsApiService replaceInstance(UnitsApiService other) {
        return INSTANCE = other;
    }

    public DefaultUnitsApiService() {
        baseSystem = new SISystem();
        systems = new HashSet<>();
        systems.add(baseSystem);

        if (isAutoDiscoveryActive()) {
            discoverSystemOfUnitsImplementations();
        }
    }

    protected DefaultUnitsApiService(Set<SystemOfUnits> systems) {
        this();
        this.systems.addAll(systems);
    }

    private boolean isAutoDiscoveryActive() {
        return Boolean.parseBoolean(System.getProperty(DEFAULT_UNIT_API_SERVICE_AUTO_LOAD, "true"));
    }

    private void discoverSystemOfUnitsImplementations() {
        ServiceLoader<SystemOfUnits> serviceLoader = ServiceLoader.load(SystemOfUnits.class);
        for (SystemOfUnits system : serviceLoader) {
            systems.add(system);
        }
    }

    @Override
    public void addSystemOfUnits(SystemOfUnits system) {
        // Check if System is already present
        boolean present = systems.stream()
                .anyMatch(it -> it.getClass().isAssignableFrom(system.getClass()));
        if (present) {
            return;
        }
        systems.add(system);
    }

    @Override
    public <T extends Quantity<T>> @NotNull Unit<T> getUnit(@NotNull Class<T> quantityType) {
        Objects.requireNonNull(quantityType);
        Unit<T> unit = baseSystem.getUnit(quantityType);
        if (unit != null) return unit;
        for (SystemOfUnits system : systems) {
            unit = system.getUnit(quantityType);
            if (unit != null) return unit;
        }
        throw new UnitNotFoundException(quantityType);
    }

    @Override
    public @NotNull Unit<?> getUnit(@NotNull String symbol) {
        Objects.requireNonNull(symbol);
        Unit<?> unit = baseSystem.getUnit(symbol);
        if (unit != null) return unit;
        for (SystemOfUnits system : systems) {
            unit = system.getUnit(symbol);
            if (unit != null) return unit;
        }
        throw new UnitNotFoundException(symbol);
    }

    @Override
    public @NotNull String format(Quantity<?> quantity) {
        return quantity.toString(); // TODO handle proper formatting
    }

    @Override
    public @NotNull Quantity<?> parseQuantity(@NotNull String quantity) {
        return (new DefaultQuantityReader()).read(quantity);
    }

    @Override
    public <Q extends Quantity<Q>> Quantity<Q> createQuantity(@NotNull Number value, @NotNull Unit<Q> unit) {
        Objects.requireNonNull(value);
        Objects.requireNonNull(unit);
        return new NumberQuantity<>(value, unit, Quantity.Scale.ABSOLUTE);
    }
}
