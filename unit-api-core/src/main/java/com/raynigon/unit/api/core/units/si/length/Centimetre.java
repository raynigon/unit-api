package com.raynigon.unit.api.core.units.si.length;

import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.MetricPrefix;
import javax.measure.quantity.Length;

public class Centimetre extends ScaledUnit<Length> {

  public Centimetre() {
    super(MetricPrefix.CENTI, new Metre(), "Centimetre");
  }
}
