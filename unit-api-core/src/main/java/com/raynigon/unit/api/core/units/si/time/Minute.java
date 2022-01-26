package com.raynigon.unit.api.core.units.si.time;

import com.raynigon.unit.api.core.units.general.ScaledUnit;
import javax.measure.quantity.Time;

public class Minute extends ScaledUnit<Time> {

  public Minute() {
    super(60, new Second(), "min", "Minute");
  }
}
