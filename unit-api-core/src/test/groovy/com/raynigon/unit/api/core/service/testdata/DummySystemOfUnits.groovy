package com.raynigon.unit.api.core.service.testdata


import com.raynigon.unit.api.core.units.general.UnitDimension

import javax.measure.Dimension
import javax.measure.Quantity
import javax.measure.Unit
import javax.measure.quantity.Length
import javax.measure.spi.SystemOfUnits

class DummySystemOfUnits implements SystemOfUnits {

    @Override
    String getName() {
        return "DummySystemOfUnits"
    }

    public <Q extends Quantity<Q>> Unit<Q> getUnit(Class<Q> quantityType) {
        return quantityType == Length.class ? new DummyUnit() : null
    }

    @Override
    public Unit<?> getUnit(String string) {
        return "WTF".equalsIgnoreCase(string) ? new DummyUnit() : null
    }

    @Override
    public Set<? extends Unit<?>> getUnits() {
        return Set.of(new DummyUnit())
    }

    @Override
    public Set<? extends Unit<?>> getUnits(Dimension dimension) {
        return dimension == UnitDimension.LENGTH ? Set.of(new DummyUnit()) : Set.of()
    }
}
