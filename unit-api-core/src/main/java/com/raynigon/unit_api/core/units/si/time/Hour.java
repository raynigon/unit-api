package com.raynigon.unit_api.core.units.si.time;

import com.raynigon.unit_api.core.units.general.ScaledUnit;
import javax.measure.quantity.Time;

public class Hour extends ScaledUnit<Time> {

  public Hour() {
    super(3600, new Second(), "h", "Hour");
  }
}
