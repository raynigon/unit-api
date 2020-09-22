package com.raynigon.unit_api.core.service;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.spi.SystemOfUnits;

public interface UnitsApiService {

    static UnitsApiService getInstance() {
        return DefaultUnitsApiService.getInstance();
    }

    /**
     * Adds an additional {@link SystemOfUnits} to the UnitsApiService.
     * The Name provided by the {@link SystemOfUnits} is used as an id
     * and therefore has to be unique.
     * @param system    the additional {@link SystemOfUnits}
     */
    void addSystemOfUnits(SystemOfUnits system);

    <T extends Quantity<T>> Unit<T> getUnit(Class<T> quantityType);

    Unit<?> getUnit(String symbol);

    String format(Quantity<?> quantity);

    Quantity<?> parseQuantity(String quantity);
}
