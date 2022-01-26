package com.raynigon.unit.api.core.units.si.dimensionless;

import com.raynigon.unit.api.core.units.si.SISystem;
import com.raynigon.unit.api.core.units.general.IUnit;
import com.raynigon.unit.api.core.units.general.ProductUnit;

import javax.measure.quantity.Dimensionless;

/**
 * Holds the dimensionless unit <code>ONE</code>.
 *
 * @see <a href= "https://en.wikipedia.org/wiki/Natural_units#Choosing_constants_to_normalize">
 *     Wikipedia: Natural Units - Choosing constants to normalize</a>
 * @see <a href= "http://www.av8n.com/physics/dimensionless-units.htm">Units of Dimension One</a>
 */
public class One extends ProductUnit<Dimensionless> implements IUnit<Dimensionless> {

  @Override
  public String getSystemId() {
    return SISystem.ID;
  }

  @Override
  public Class<Dimensionless> getQuantityType() {
    return Dimensionless.class;
  }
}
