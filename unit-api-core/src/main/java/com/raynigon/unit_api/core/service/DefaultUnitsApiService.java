package com.raynigon.unit_api.core.service;

import com.raynigon.unit_api.core.quantities.NumberQuantity;
import com.raynigon.unit_api.core.units.si.SISystem;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.spi.SystemOfUnits;
import java.util.HashSet;
import java.util.Set;

public class DefaultUnitsApiService implements UnitsApiService {

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

    }

    protected DefaultUnitsApiService(Set<SystemOfUnits> systems) {
        this();
        this.systems.addAll(systems);
    }

    @Override
    public void addSystemOfUnits(SystemOfUnits system) {
        // Check if System is already present
        boolean present = systems.stream().anyMatch(it -> it.getClass().isAssignableFrom(system.getClass()));
        if (present) {
            return;
        }
        systems.add(system);
    }

    @Override
    public <T extends Quantity<T>> Unit<T> getUnit(Class<T> quantityType) {
        Unit<T> unit = baseSystem.getUnit(quantityType);
        if (unit != null) return unit;
        for (SystemOfUnits system : systems) {
            unit = system.getUnit(quantityType);
            if (unit != null) return unit;
        }
        return null;
    }

    @Override
    public Unit<?> getUnit(String symbol) {
        Unit<?> unit = baseSystem.getUnit(symbol);
        if (unit != null) return unit;
        for (SystemOfUnits system : systems) {
            unit = system.getUnit(symbol);
            if (unit != null) return unit;
        }
        return null;
    }

    @Override
    public String format(Quantity<?> quantity) {
        return quantity.toString(); // TODO handle proper formatting
    }

    @Override
    public Quantity<?> parseQuantity(String quantity) {
        String[] parts = quantity.split(" ");
        Double value = Double.parseDouble(parts[0]);
        String symbol = parts[1];
        Unit<?> unit = getUnit(symbol);
        if (unit == null) return null;
        return new NumberQuantity<>(value, unit);
    }

    @Override
    public <Q extends Quantity<Q>> Quantity<Q> createQuantity(Number value, Unit<Q> unit) {
        return new NumberQuantity<Q>(value, unit, Quantity.Scale.ABSOLUTE);
    }
}
