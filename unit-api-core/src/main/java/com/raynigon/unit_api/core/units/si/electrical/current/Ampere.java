package com.raynigon.unit_api.core.units.si.electrical.current;

import com.raynigon.unit_api.core.units.general.BaseUnit;
import com.raynigon.unit_api.core.units.si.SISystem;
import tech.units.indriya.unit.UnitDimension;

import javax.measure.quantity.ElectricCurrent;

public class Ampere extends BaseUnit<ElectricCurrent> {

    public Ampere() {
        super(SISystem.ID, "A", "Ampere", ElectricCurrent.class, UnitDimension.ELECTRIC_CURRENT);
    }
}
