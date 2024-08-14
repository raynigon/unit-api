package com.raynigon.unit.api.core.units.binary;

import com.raynigon.unit.api.core.units.general.IUnit;
import com.raynigon.unit.api.core.units.general.UnitScanUtils;

import javax.measure.Dimension;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.spi.SystemOfUnits;
import java.util.*;
import java.util.stream.Collectors;

public class BinarySystem implements SystemOfUnits {
    public static final String ID = "Binary";

    private final Set<IUnit<?>> units = new HashSet<>();

    private final Map<Class<?>, IUnit<?>> systemUnits = new HashMap<>();

    private final Map<String, IUnit<?>> symbolToUnit = new HashMap<>();

    public BinarySystem() {
        init();
    }

    private void init() {
        UnitScanUtils.scanForUnits(BinarySystem.class.getPackage()).forEach(this::addUnit);
    }

    private void addUnit(IUnit<?> unit) {
        this.units.add(unit);
        symbolToUnit.put(unit.getSymbol(), unit);
        if (unit.isSystemUnit()) {
            systemUnits.put(unit.getQuantityType(), unit);
        }
    }


    @Override
    public String getName() {
        return ID;
    }

    @Override
    public <Q extends Quantity<Q>> Unit<Q> getUnit(Class<Q> quantityType) {
        //noinspection unchecked
        return (Unit<Q>) systemUnits.get(quantityType);
    }

    @Override
    public Unit<?> getUnit(String string) {
        return symbolToUnit.get(string);
    }

    @Override
    public Set<? extends Unit<?>> getUnits() {
        return Collections.unmodifiableSet(units);
    }

    @Override
    public Set<? extends Unit<?>> getUnits(Dimension dimension) {
        return units.stream()
                .filter(unit -> unit.getDimension().equals(dimension))
                .collect(Collectors.toUnmodifiableSet());
    }
}
