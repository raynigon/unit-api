package com.raynigon.unit_api.core.service;

import com.raynigon.unit_api.core.format.SimpleQuantityFormat;
import com.raynigon.unit_api.core.units.si.SISystem;
import tech.units.indriya.quantity.Quantities;

import javax.measure.Quantity;
import javax.measure.Unit;

public class UnitsApiService {

    private static final UnitsApiService INSTANCE = new UnitsApiService();

    public static UnitsApiService getInstance() {
        return INSTANCE;
    }

    private final SISystem system;

    public UnitsApiService() {
        system = new SISystem();
    }

    public <T extends Quantity<T>> Unit<T> getUnit(Class<T> quantityType) {
        return system.getUnit(quantityType);
    }

    public Unit<?> getUnit(String symbol) {
        return system.getUnit(symbol);
    }

    public String format(Quantity<?> quantity) {
        return SimpleQuantityFormat.getInstance().format(quantity);
    }

    public Quantity<?> parseQuantity(String quantity) {
        String[] parts = quantity.split(" ");
        Double value = Double.parseDouble(parts[0]);
        String symbol = parts[1];
        Unit<?> unit = system.getUnit(symbol);
        return Quantities.getQuantity(value, unit);
    }
}
