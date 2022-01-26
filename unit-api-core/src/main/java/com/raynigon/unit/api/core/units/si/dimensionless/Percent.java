package com.raynigon.unit.api.core.units.si.dimensionless;

import com.raynigon.unit.api.core.units.general.TransformedUnit;
import com.raynigon.unit.api.core.units.si.SISystem;
import com.raynigon.unit.api.core.function.unitconverter.MultiplyConverter;
import com.raynigon.unit.api.core.units.general.IUnit;

import javax.measure.quantity.Dimensionless;

public class Percent extends TransformedUnit<Dimensionless> implements IUnit<Dimensionless> {

  public Percent() {
    super("%", "Percent", new One(), new One(), MultiplyConverter.ofRational(1, 100));
  }

  @Override
  public String getSystemId() {
    return SISystem.ID;
  }

  @Override
  public Class<Dimensionless> getQuantityType() {
    return Dimensionless.class;
  }
}
