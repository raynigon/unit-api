package com.raynigon.unit.api.core.units.binary.bytes;

import com.raynigon.unit.api.core.units.binary.BinarySystem;
import com.raynigon.unit.api.core.units.general.BaseUnit;
import com.raynigon.unit.api.core.units.general.UnitDimension;

import javax.measure.quantity.Dimensionless;

public class Byte extends BaseUnit<Dimensionless> {
    public Byte() {
        super(BinarySystem.ID, "B", "Byte", Dimensionless.class, UnitDimension.NONE);
    }
}
