package com.raynigon.unit_api.core.units.si.speed;

import com.raynigon.unit_api.core.units.general.AlternateUnit;
import com.raynigon.unit_api.core.units.si.SISystem;
import com.raynigon.unit_api.core.units.si.length.Metre;
import com.raynigon.unit_api.core.units.si.time.Second;
import javax.measure.Unit;
import javax.measure.quantity.Speed;

public class MetrePerSecond extends AlternateUnit<Speed> {

  @SuppressWarnings("unchecked")
  public MetrePerSecond() {
    super(
        SISystem.ID,
        "m/s",
        "Metre per Second",
        (Unit<Speed>) new Metre().divide(new Second()),
        Speed.class);
  }
}
