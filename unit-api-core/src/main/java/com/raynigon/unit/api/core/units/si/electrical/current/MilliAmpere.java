package com.raynigon.unit.api.core.units.si.electrical.current;

import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.MetricPrefix;
import javax.measure.quantity.ElectricCurrent;

public class MilliAmpere extends ScaledUnit<ElectricCurrent> {

  public MilliAmpere() {
    super(MetricPrefix.MILLI, new Ampere(), "MilliAmpere");
  }
}
