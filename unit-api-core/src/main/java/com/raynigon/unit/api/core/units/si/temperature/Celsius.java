package com.raynigon.unit.api.core.units.si.temperature;

import com.raynigon.unit.api.core.units.si.SISystem;
import com.raynigon.unit.api.core.function.unitconverter.AddConverter;
import com.raynigon.unit.api.core.units.general.IUnit;
import com.raynigon.unit.api.core.units.general.TransformedUnit;

import javax.measure.quantity.Temperature;

public class Celsius extends TransformedUnit<Temperature> implements IUnit<Temperature> {

  @SuppressWarnings("unchecked")
  public Celsius() {
    super(
            "°C",
            "Celsius",
            new Kelvin(),
            new Kelvin(),
            AddConverter.of(273.15));
  }

  @Override
  public String getSystemId() {
    return SISystem.ID;
  }

  @Override
  public Class<Temperature> getQuantityType() {
    return Temperature.class;
  }

    @Override
    public String[] getSymbolAliases() {
        return new String[]{"\u2103"};
    }
}
