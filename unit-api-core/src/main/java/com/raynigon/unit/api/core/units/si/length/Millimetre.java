package com.raynigon.unit.api.core.units.si.length;

import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.MetricPrefix;
import javax.measure.quantity.Length;

public class Millimetre extends ScaledUnit<Length> {

  public Millimetre() {
    super(MetricPrefix.MILLI, new Metre(), "Millimetre");
  }
}
