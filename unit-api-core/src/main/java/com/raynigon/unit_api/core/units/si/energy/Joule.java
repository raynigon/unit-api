package com.raynigon.unit_api.core.units.si.energy;

import com.raynigon.unit_api.core.units.general.AlternateUnit;
import com.raynigon.unit_api.core.units.si.SISystem;
import com.raynigon.unit_api.core.units.si.force.Newton;
import com.raynigon.unit_api.core.units.si.length.Metre;
import javax.measure.Unit;
import javax.measure.quantity.Energy;

public class Joule extends AlternateUnit<Energy> {

  @SuppressWarnings("unchecked")
  public Joule() {
    super(
        SISystem.ID, "J", "Joule", (Unit<Energy>) new Newton().multiply(new Metre()), Energy.class);
  }
}
