package com.raynigon.unit_api.core.service;

import com.raynigon.unit_api.core.format.SimpleQuantityFormat;
import com.raynigon.unit_api.core.units.si.SISystem;
import tech.units.indriya.quantity.Quantities;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.spi.SystemOfUnits;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UnitsApiService {

    private static UnitsApiService INSTANCE = new UnitsApiService();

    public static UnitsApiService getInstance() {
        return INSTANCE;
    }

    protected static UnitsApiService replaceInstance(UnitsApiService other) {
        return INSTANCE = other;
    }

    private final SISystem baseSystem;

    private final Set<SystemOfUnits> systems;

    public UnitsApiService() {
        baseSystem = new SISystem();
        systems = new HashSet<>();
        systems.add(baseSystem);

    }

    protected UnitsApiService(Set<SystemOfUnits> systems) {
        this();
        this.systems.addAll(systems);
    }

    public void addSystemOfUnits(SystemOfUnits system) {
        systems.add(system);
    }

    public <T extends Quantity<T>> Unit<T> getUnit(Class<T> quantityType) {
        Unit<T> unit = baseSystem.getUnit(quantityType);
        if (unit != null) return unit;
        for (SystemOfUnits system : systems) {
            unit = system.getUnit(quantityType);
            if (unit != null) return unit;
        }
        return null;
    }

    public Unit<?> getUnit(String symbol) {
        Unit<?> unit = baseSystem.getUnit(symbol);
        if (unit != null) return unit;
        for (SystemOfUnits system : systems) {
            unit = system.getUnit(symbol);
            if (unit != null) return unit;
        }
        return null;
    }

    public String format(Quantity<?> quantity) {
        return SimpleQuantityFormat.getInstance().format(quantity);
    }

    public Quantity<?> parseQuantity(String quantity) {
        String[] parts = quantity.split(" ");
        Double value = Double.parseDouble(parts[0]);
        String symbol = parts[1];
        Unit<?> unit = getUnit(symbol);
        if (unit == null) return null;
        return Quantities.getQuantity(value, unit);
    }
}
