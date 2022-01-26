package com.raynigon.unit.api.core.units.si.electrical.potential;

import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.MetricPrefix;
import javax.measure.quantity.ElectricPotential;

public class MilliVolt extends ScaledUnit<ElectricPotential> {

  public MilliVolt() {
    super(MetricPrefix.MILLI, new Volt(), "MilliVolt");
  }
}
