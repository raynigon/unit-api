package com.raynigon.unit.api.core.units.general;

import javax.measure.Quantity;
import javax.measure.Unit;

public interface IUnit<Q extends Quantity<Q>> extends Unit<Q> {

  String getSystemId();

  Class<Q> getQuantityType();

  default boolean isSystemUnit() {
    return getSystemUnit().getClass().equals(this.getClass());
  }
}
