package com.raynigon.unit_api.core.units.general;

import tech.units.indriya.AbstractUnit;

import javax.measure.*;

import java.util.Map;
import java.util.Objects;

public class AlternateUnit<Q extends Quantity<Q>> extends AbstractUnit<Q> implements IUnit<Q> {


    /**
     * Holds the parent unit (a system unit).
     */
    private final Unit<Q> parentUnit;
    private final String systemId;
    private final Class<Q> quantityType;

    @SuppressWarnings("rawtypes")
    public AlternateUnit(String systemId, String symbol, String name, Unit<Q> parentUnit, Class<Q> quantityType) {
        super(Objects.requireNonNull(symbol));
        Objects.requireNonNull(systemId);
        Objects.requireNonNull(name);
        Objects.requireNonNull(quantityType);
        Objects.requireNonNull(parentUnit);

        this.systemId = systemId;
        this.name = name;
        if (!(parentUnit instanceof AbstractUnit))
            throw new IllegalArgumentException("The parent unit: " + parentUnit + " is not an AbstractUnit");
        if (!((AbstractUnit) parentUnit).isSystemUnit())
            throw new IllegalArgumentException("The parent unit: " + parentUnit + " is not an unscaled SI unit");
        this.parentUnit = parentUnit instanceof AlternateUnit ? ((AlternateUnit) parentUnit).getParentUnit()
                : parentUnit;
        this.quantityType = quantityType;
    }

    /**
     * Returns the parent unit of this alternate unit, always a system unit and
     * never an alternate unit.
     *
     * @return the parent unit.
     */
    public Unit<?> getParentUnit() {
        return parentUnit;
    }

    @Override
    public String getSystemId() {
        return systemId;
    }

    @Override
    public Class<Q> getQuantityType() {
        return quantityType;
    }

    @Override
    public Dimension getDimension() {
        return parentUnit.getDimension();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public UnitConverter getSystemConverter() {
        return ((AbstractUnit) parentUnit).getSystemConverter();
    }

    @Override
    public Unit<Q> toSystemUnit() {
        return this; // Alternate units are SI units.
    }

    @Override
    public Map<? extends Unit<?>, Integer> getBaseUnits() {
        return parentUnit.getBaseUnits();
    }

    @Override
    public int hashCode() {
        return Objects.hash(parentUnit, getSymbol());
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AlternateUnit) {
            AlternateUnit that = (AlternateUnit) obj;
            return Objects.equals(parentUnit, that.parentUnit) && Objects.equals(getSymbol(), that.getSymbol());
        }
        return false;
    }
}
