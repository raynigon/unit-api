package com.raynigon.unit.api.core.units.general;

import javax.measure.Quantity;
import javax.measure.Unit;

public interface IUnit<Q extends Quantity<Q>> extends Unit<Q> {

    String getSystemId();

    Class<Q> getQuantityType();

    /**
     * Symbol aliases which are also accepted for parsing.
     * This is needed because some units have multiple commonly used symbols.
     * One example is the Newton meter which is commonly written as "N m" or "N⋅m".
     * Another one is the degree Celsius which is commonly written as "°C" or "℃".
     */
    default String[] getSymbolAliases() {
        return new String[]{};
    }

    default boolean isSystemUnit() {
        return getSystemUnit().getClass().equals(this.getClass());
    }
}
