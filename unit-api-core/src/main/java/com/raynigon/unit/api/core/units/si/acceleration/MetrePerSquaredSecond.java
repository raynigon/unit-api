package com.raynigon.unit.api.core.units.si.acceleration;

import com.raynigon.unit.api.core.units.si.SISystem;
import com.raynigon.unit.api.core.units.si.time.Second;
import com.raynigon.unit.api.core.units.general.AlternateUnit;
import com.raynigon.unit.api.core.units.si.length.Metre;

import javax.measure.Unit;
import javax.measure.quantity.Acceleration;

public class MetrePerSquaredSecond extends AlternateUnit<Acceleration> {

  @SuppressWarnings("unchecked")
  public MetrePerSquaredSecond() {
    super(
        SISystem.ID,
        "m/s²",
        "Metre per second squared",
        (Unit<Acceleration>) new Metre().divide(new Second().pow(2)),
        Acceleration.class);
  }
}
