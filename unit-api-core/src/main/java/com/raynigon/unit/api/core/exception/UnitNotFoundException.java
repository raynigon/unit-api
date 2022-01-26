package com.raynigon.unit.api.core.exception;

import org.jetbrains.annotations.NotNull;

import javax.measure.Quantity;
import java.util.Objects;

public class UnitNotFoundException extends RuntimeException {
    public UnitNotFoundException(@NotNull String symbol) {
        super("No Unit was found for symbol" + symbol);
        Objects.requireNonNull(symbol);
    }

    public <T extends Quantity<T>> UnitNotFoundException(@NotNull Class<T> quantityType) {
        super("No Unit was found for QuantityType " + quantityType.getName());
        Objects.requireNonNull(quantityType);
    }

    public UnitNotFoundException(Class<?> quantityType, String message) {
        super("No Unit was found for QuantityType " + quantityType.getName() + "\n" + message);
        Objects.requireNonNull(quantityType);
    }
}
