package com.raynigon.unit_api.core.units.general;

import com.raynigon.unit_api.core.function.AbstractConverter;

import javax.measure.Dimension;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.UnitConverter;

import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class represents the building blocks on top of which all others physical
 * units are created. Base units are always unscaled SI units.
 * </p>
 *
 *
 * @see <a href="http://en.wikipedia.org/wiki/SI_base_unit"> Wikipedia: SI base
 *      unit</a>
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:werner@units.tech">Werner Keil</a>
 * @version 2.0, February 7, 2020
 * @since 1.0
 */
public class BaseUnit<Q extends Quantity<Q>> extends AbstractUnit<Q> implements IUnit<Q> {


    /**
     * Holds the base unit dimension.
     */
    private final Dimension dimension;
    private final String systemId;
    private final Class<Q> quantityType;

    /**
     * Creates a base unit having the specified symbol, name and dimension.
     *
     * @param symbol the symbol of this base unit.
     * @param name   the name of this base unit.
     * @throws IllegalArgumentException if the specified symbol is associated to a
     *                                  different unit.
     * @since 2.0
     */
    public BaseUnit(String systemId, String symbol, String name, Class<Q> quantityType, Dimension dimension) {
        super(Objects.requireNonNull(symbol));
        Objects.requireNonNull(systemId);
        Objects.requireNonNull(quantityType);
        Objects.requireNonNull(name);
        Objects.requireNonNull(dimension);
        this.systemId = systemId;
        this.quantityType = quantityType;
        this.name = name;
        this.dimension = dimension;
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
    public Unit<Q> toSystemUnit() {
        return this;
    }

    @Override
    public UnitConverter getSystemConverter() throws UnsupportedOperationException {
        return AbstractConverter.IDENTITY;
    }

    @Override
    public Dimension getDimension() {
        return dimension;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSymbol() == null) ? 0 : getSymbol().hashCode());
        // result = result + prime * result + ((name == null) ? 0 : name.hashCode());
        result = result + prime * result + ((dimension == null) ? 0 : dimension.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        @SuppressWarnings("rawtypes")
        BaseUnit other = (BaseUnit) obj;
        return Objects.equals(dimension, other.getDimension()) && Objects.equals(getSymbol(), other.getSymbol());
    }

    @Override
    public Map<? extends AbstractUnit<Q>, Integer> getBaseUnits() {
        return null;
    }
}
