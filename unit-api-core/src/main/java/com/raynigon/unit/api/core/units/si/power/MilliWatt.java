package com.raynigon.unit.api.core.units.si.power;

import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.MetricPrefix;
import javax.measure.quantity.Power;

public class MilliWatt extends ScaledUnit<Power> {

  public MilliWatt() {
    super(MetricPrefix.MILLI, new Watt(), "MilliWatt");
  }
}
