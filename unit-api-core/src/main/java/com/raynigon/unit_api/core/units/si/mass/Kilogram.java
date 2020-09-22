package com.raynigon.unit_api.core.units.si.mass;

import com.raynigon.unit_api.core.units.general.BaseUnit;
import com.raynigon.unit_api.core.units.si.SISystem;
import tech.units.indriya.unit.UnitDimension;

import javax.measure.quantity.Mass;

public class Kilogram extends BaseUnit<Mass> {

    public Kilogram() {
        super(SISystem.ID, "kg", "Kilogram", Mass.class, UnitDimension.MASS);
    }
}
