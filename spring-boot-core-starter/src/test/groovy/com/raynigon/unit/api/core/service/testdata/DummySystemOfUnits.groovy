package com.raynigon.unit.api.core.service.testdata

import com.raynigon.unit.api.core.units.si.length.Metre

import javax.measure.Dimension
import javax.measure.Quantity
import javax.measure.Unit
import javax.measure.spi.SystemOfUnits

class DummySystemOfUnits implements SystemOfUnits {

    @Override
    String getName() {
        return "DummySystemOfUnits"
    }

    def <Q extends Quantity<Q>> Unit<Q> getUnit(Class<Q> quantityType) {
        return null
    }

    @Override
    Unit<?> getUnit(String string) {
        return "WTF".equalsIgnoreCase(string) ? new Metre() : null
    }

    @Override
    Set<? extends Unit<?>> getUnits() {
        return null
    }

    @Override
    Set<? extends Unit<?>> getUnits(Dimension dimension) {
        return null
    }
}
