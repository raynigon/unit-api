package com.raynigon.unit.api.core.units.general;

import com.raynigon.unit.api.core.function.unitconverter.MultiplyConverter;

import java.util.Objects;
import javax.measure.Prefix;
import javax.measure.Quantity;
import javax.measure.UnitConverter;

/**
 *
 * */
public class ScaledUnit<Q extends Quantity<Q>> extends TransformedUnit<Q> implements IUnit<Q> {

    private final String systemId;
    private final Class<Q> quantityType;

    private static <Q extends Quantity<Q>> String generateSymbol(Prefix prefix, IUnit<Q> parent) {
        Objects.requireNonNull(prefix.getSymbol());
        Objects.requireNonNull(parent.getSymbol());
        return prefix.getSymbol() + parent.getSymbol();
    }

    /**
     * @param prefix
     * @param parent
     * @param name
     */
    public ScaledUnit(Prefix prefix, IUnit<Q> parent, String name) {
        this(MultiplyConverter.ofPrefix(prefix), parent, generateSymbol(prefix, parent), name);
    }

    /**
     * @param factor
     * @param parent
     * @param symbol
     * @param name
     */
    public ScaledUnit(Number factor, IUnit<Q> parent, String symbol, String name) {
        this(MultiplyConverter.of(factor), parent, symbol, name);
    }

    /**
     * @param converter
     * @param parent
     * @param symbol
     * @param name
     */
    public ScaledUnit(UnitConverter converter, IUnit<Q> parent, String symbol, String name) {
        super(symbol, Objects.requireNonNull(name), parent, parent.getSystemUnit(), converter);
        Objects.requireNonNull(name);
        this.systemId = parent.getSystemId();
        this.quantityType = parent.getQuantityType();
    }

    @Override
    public String getSystemId() {
        return systemId;
    }

    @Override
    public Class<Q> getQuantityType() {
        return quantityType;
    }
}
