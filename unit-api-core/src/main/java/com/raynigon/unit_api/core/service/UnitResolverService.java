package com.raynigon.unit_api.core.service;

import tech.units.indriya.AbstractSystemOfUnits;
import tech.units.indriya.function.AbstractConverter;
import tech.units.indriya.function.MultiplyConverter;
import tech.units.indriya.function.RationalConverter;
import tech.units.indriya.unit.TransformedUnit;
import tech.units.indriya.unit.Units;

import javax.measure.MetricPrefix;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.ElectricCharge;
import javax.measure.quantity.Energy;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UnitResolverService extends AbstractSystemOfUnits {

    private static final UnitResolverService INSTANCE = new UnitResolverService();

    public UnitResolverService() {
        units.addAll(Units.getInstance().getUnits());

        createCustomUnits();
        replaceDefaultUnits();

        createScaledUnits("m", "k", "c", "m");
        createScaledUnits("Wh", "k", "m");
        createScaledUnits("s", "m", "\u00b5", "n");
    }

    public <U extends Unit<?>> void addUnit(U unit) {
        units.add(unit);
    }

    public <U extends Unit<?>> void addUnit(U unit, Class<? extends Quantity<?>> type) {
        units.add(unit);
        quantityToUnit.put(type, unit);
    }

    public <U extends Quantity<U>> void replaceUnit(Unit<U> oldUnit, Unit<U> newUnit) {
        units.remove(oldUnit);
        @SuppressWarnings("rawtypes")
        Set<Class<? extends Quantity>> keys = quantityToUnit.entrySet()
                .stream()
                .filter((it) -> it.getValue().equals(oldUnit))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        keys.forEach(quantityToUnit::remove);
        if (keys.isEmpty()) {
            addUnit(newUnit);
            return;
        }
        //noinspection unchecked
        Class<? extends Quantity<?>> quantityType = (Class<? extends Quantity<?>>) new ArrayList<>(keys).get(0);
        addUnit(newUnit, quantityType);
    }

    private void createCustomUnits() {
        @SuppressWarnings("unchecked")
        Unit<Energy> wattHour = new TransformedUnit<Energy>(
                "Wh",
                "Watt-hour",
                (Unit<Energy>) Units.WATT.multiply(Units.HOUR),
                AbstractConverter.IDENTITY);
        addUnit(wattHour);

        Unit<ElectricCharge> ampereSecond = new TransformedUnit<ElectricCharge>(
                "As",
                "Ampere-second",
                Units.COULOMB,
                AbstractConverter.IDENTITY);
        addUnit(ampereSecond);

        Unit<ElectricCharge> ampereHour = new TransformedUnit<ElectricCharge>(
                "Ah",
                "Ampere-hour",
                (Unit<ElectricCharge>) Units.COULOMB.multiply(Units.HOUR),
                AbstractConverter.IDENTITY);
        addUnit(ampereHour);
    }

    private void replaceDefaultUnits() {
        // Replace Unit, due to missing Symbol
        replaceUnit(Units.METRE_PER_SQUARE_SECOND, new TransformedUnit<>(
                "m/s²",
                "Metre per second squared",
                Units.METRE_PER_SQUARE_SECOND,
                AbstractConverter.IDENTITY
        ));
    }

    private void createScaledUnits(String base, String... prefixes) {
        Unit<?> baseUnit = getUnit(base);
        Map<String, MetricPrefix> prefixMap = Stream.of(MetricPrefix.values()).collect(
                Collectors.toMap(MetricPrefix::getSymbol, it -> it)
        );
        for (String prefix : prefixes) {
            units.add(baseUnit.prefix(prefixMap.get(prefix)));
        }
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public <Q extends Quantity<Q>> Unit<Q> getUnit(Class<Q> quantityType) {
        return Units.getInstance().getUnit(quantityType);
    }

    public static UnitResolverService getInstance() {
        return INSTANCE;
    }
}
