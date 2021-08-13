package com.raynigon.unit_api.core.units.si.time;

import com.raynigon.unit_api.core.units.general.ScaledUnit;

import javax.measure.MetricPrefix;
import javax.measure.quantity.Time;

public class NanoSecond extends ScaledUnit<Time> {

  public NanoSecond() {
    super(MetricPrefix.NANO, new Second(), "NanoSecond");
  }
}
