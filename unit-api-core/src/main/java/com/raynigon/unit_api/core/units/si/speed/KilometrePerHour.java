package com.raynigon.unit_api.core.units.si.speed;

import com.raynigon.unit_api.core.function.unitconverter.MultiplyConverter;
import com.raynigon.unit_api.core.units.general.IUnit;
import com.raynigon.unit_api.core.units.general.TransformedUnit;
import com.raynigon.unit_api.core.units.si.SISystem;
import com.raynigon.unit_api.core.units.si.length.Kilometre;
import com.raynigon.unit_api.core.units.si.time.Hour;
import javax.measure.Unit;
import javax.measure.quantity.Speed;

public class KilometrePerHour extends TransformedUnit<Speed> implements IUnit<Speed> {

  @SuppressWarnings("unchecked")
  public KilometrePerHour() {
    super(
        "km/h",
        "Kilometre per Hour",
        (Unit<Speed>) new Kilometre().divide(new Hour()),
        new MetrePerSecond(),
        MultiplyConverter.of(1));
  }

  @Override
  public String getSystemId() {
    return SISystem.ID;
  }

  @Override
  public Class<Speed> getQuantityType() {
    return Speed.class;
  }

  @Override
  protected Unit<Speed> toSystemUnit() {
    return new MetrePerSecond();
  }
}
