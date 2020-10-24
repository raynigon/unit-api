package com.raynigon.unit_api.core.units.si.energy;

import com.raynigon.unit_api.core.function.unitconverter.MultiplyConverter;
import com.raynigon.unit_api.core.units.general.IUnit;
import com.raynigon.unit_api.core.units.general.TransformedUnit;
import com.raynigon.unit_api.core.units.si.SISystem;
import com.raynigon.unit_api.core.units.si.power.Watt;
import com.raynigon.unit_api.core.units.si.time.Hour;
import javax.measure.Unit;
import javax.measure.quantity.Energy;

public class WattHour extends TransformedUnit<Energy> implements IUnit<Energy> {

  @SuppressWarnings("unchecked")
  public WattHour() {
    super(
        "Wh",
        "WattHour",
        (Unit<Energy>) new Watt().multiply(new Hour()),
        new Joule(),
        MultiplyConverter.of(1));
  }

  @Override
  public String getSystemId() {
    return SISystem.ID;
  }

  @Override
  public Class<Energy> getQuantityType() {
    return Energy.class;
  }
}
