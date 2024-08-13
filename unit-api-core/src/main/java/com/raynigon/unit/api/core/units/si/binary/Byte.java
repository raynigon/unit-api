package com.raynigon.unit.api.core.units.si.binary;

import com.raynigon.unit.api.core.units.general.BaseUnit;
import com.raynigon.unit.api.core.units.general.UnitDimension;
import com.raynigon.unit.api.core.units.si.SISystem;

import javax.measure.quantity.Dimensionless;

public class Byte extends BaseUnit<Dimensionless> {
    public Byte() {
        super(SISystem.ID, "B", "Byte", Dimensionless.class, UnitDimension.NONE);
    }
}
