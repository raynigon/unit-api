package com.raynigon.unit_api.core.units.si;

import com.raynigon.unit_api.core.units.general.IUnit;
import com.raynigon.unit_api.core.units.general.UnitScanUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.measure.Dimension;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.spi.SystemOfUnits;

public class SISystem implements SystemOfUnits {

  public static final String ID = "SI";

  private final Set<IUnit<?>> units = new HashSet<>();

  private final Map<Class<?>, IUnit<?>> systemUnits = new HashMap<>();

  private final Map<String, IUnit<?>> symbolToUnit = new HashMap<>();

  public SISystem() {
    init();
  }

  private void init() {
    UnitScanUtils.scanForUnits(SISystem.class.getPackage()).forEach(this::addUnit);
  }

  private void addUnit(IUnit<?> unit) {
    this.units.add(unit);
    symbolToUnit.put(unit.getSymbol(), unit);
    if (unit.isSystemUnit()) {
      systemUnits.put(unit.getQuantityType(), unit);
    }
  }

  @Override
  public String getName() {
    return ID;
  }

  @Override
  public <Q extends Quantity<Q>> Unit<Q> getUnit(Class<Q> quantityType) {
    //noinspection unchecked
    return (Unit<Q>) systemUnits.get(quantityType);
  }

  @Override
  public IUnit<?> getUnit(String symbol) {
    return symbolToUnit.get(symbol);
  }

  @Override
  public Set<? extends IUnit<?>> getUnits() {
    return Collections.unmodifiableSet(this.units);
  }

  @Override
  public Set<? extends IUnit<?>> getUnits(Dimension dimension) {
    return this.units.stream()
        .filter(unit -> unit.getDimension().equals(dimension))
        .collect(Collectors.toSet());
  }
}
