package com.raynigon.unit.api.core.units.si.frequency;

import com.raynigon.unit.api.core.units.si.SISystem;
import com.raynigon.unit.api.core.units.si.dimensionless.One;
import com.raynigon.unit.api.core.units.si.time.Second;
import com.raynigon.unit.api.core.units.general.AlternateUnit;

import javax.measure.Unit;
import javax.measure.quantity.Frequency;

public class Hertz extends AlternateUnit<Frequency> {

  @SuppressWarnings("unchecked")
  public Hertz() {
    super(
        SISystem.ID,
        "Hz",
        "Hertz",
        (Unit<Frequency>) new One().divide(new Second()),
        Frequency.class);
  }
}
