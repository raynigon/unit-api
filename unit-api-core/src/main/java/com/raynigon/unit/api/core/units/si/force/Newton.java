package com.raynigon.unit.api.core.units.si.force;

import com.raynigon.unit.api.core.units.si.SISystem;
import com.raynigon.unit.api.core.units.si.length.Metre;
import com.raynigon.unit.api.core.units.si.mass.Kilogram;
import com.raynigon.unit.api.core.units.si.time.Second;
import com.raynigon.unit.api.core.units.general.AlternateUnit;

import javax.measure.Unit;
import javax.measure.quantity.Force;

public class Newton extends AlternateUnit<Force> {

  @SuppressWarnings("unchecked")
  public Newton() {
    super(
        SISystem.ID,
        "N",
        "Newton",
        (Unit<Force>) new Metre().multiply(new Kilogram()).divide(new Second().pow(2)),
        Force.class);
  }
}
