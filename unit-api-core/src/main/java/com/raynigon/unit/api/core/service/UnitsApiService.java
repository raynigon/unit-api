package com.raynigon.unit.api.core.service;

import com.raynigon.unit.api.core.exception.UnitNotFoundException;
import com.raynigon.unit.api.core.io.QuantityReader;
import com.raynigon.unit.api.core.io.QuantityWriter;
import org.jetbrains.annotations.NotNull;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.spi.SystemOfUnits;

public interface UnitsApiService {

    static @NotNull UnitsApiService getInstance() {
        return DefaultUnitsApiService.getInstance();
    }

    static <T extends Quantity<T>> @NotNull Unit<T> unit(@NotNull Class<T> quantityType) {
        return getInstance().getUnit(quantityType);
    }

    static @NotNull Unit<?> unit(@NotNull String symbol) {
        return getInstance().getUnit(symbol);
    }

    @Deprecated
    static @NotNull Quantity<?> quantity(@NotNull String quantity) {
        return getInstance().parseQuantity(quantity);
    }

    static @NotNull QuantityReader reader() {
        return getInstance().defaultReader();
    }

    static @NotNull QuantityWriter writer() {
        return getInstance().defaultWriter();
    }

    static <Q extends Quantity<Q>> @NotNull Quantity<Q> quantity(@NotNull Number number, @NotNull Unit<Q> unit) {
        return getInstance().createQuantity(number, unit);
    }

    /**
     * Adds an additional {@link SystemOfUnits} to the UnitsApiService. The Name provided by the
     * {@link SystemOfUnits} is used as an id and therefore has to be unique.
     *
     * @param system the additional {@link SystemOfUnits}
     */
    void addSystemOfUnits(SystemOfUnits system);

    <T extends Quantity<T>> @NotNull Unit<T> getUnit(Class<T> quantityType) throws UnitNotFoundException;

    @NotNull Unit<?> getUnit(@NotNull String symbol) throws UnitNotFoundException;

    @NotNull String format(@NotNull Quantity<?> quantity);

    @NotNull Quantity<?> parseQuantity(@NotNull String quantity);

    <Q extends Quantity<Q>> Quantity<Q> createQuantity(@NotNull Number number, @NotNull Unit<Q> unit);

    default @NotNull QuantityReader defaultReader() {
        return (String quantity) -> getInstance().parseQuantity(quantity);
    }

    default @NotNull QuantityWriter defaultWriter() {
        return Object::toString;
    }
}
