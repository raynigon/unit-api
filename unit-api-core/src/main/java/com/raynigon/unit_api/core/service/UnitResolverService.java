package com.raynigon.unit_api.core.service;

import tech.units.indriya.AbstractSystemOfUnits;
import tech.units.indriya.function.AbstractConverter;
import tech.units.indriya.unit.TransformedUnit;
import tech.units.indriya.unit.Units;

import javax.measure.MetricPrefix;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Acceleration;
import javax.measure.quantity.Energy;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class UnitResolverService extends AbstractSystemOfUnits {

    private static final UnitResolverService INSTANCE = new UnitResolverService();

    public UnitResolverService() {
        units.addAll(Units.getInstance().getUnits());

        @SuppressWarnings("unchecked")
        Unit<Energy> wattHour = new TransformedUnit<Energy>(
                "Wh",
                "Watt-hour",
                (Unit<Energy>) Units.WATT.multiply(Units.HOUR),
                AbstractConverter.IDENTITY);
        units.add(wattHour);
        units.remove(Units.METRE_PER_SQUARE_SECOND);
        Unit<Acceleration> metrePerSquareSecond = new TransformedUnit<>("m/s²", "", Units.METRE_PER_SQUARE_SECOND,
                AbstractConverter.IDENTITY);
        units.add(metrePerSquareSecond);
        createScaledUnits("m", "k", "c", "m");
        createScaledUnits("Wh", "k", "m");
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
