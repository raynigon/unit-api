package com.raynigon.unit.api.core.units.si.energy;

import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.MetricPrefix;
import javax.measure.quantity.Energy;

public class MilliWattHour extends ScaledUnit<Energy> {

  public MilliWattHour() {
    super(MetricPrefix.MILLI, new WattHour(), "MilliWattHour");
  }
}
