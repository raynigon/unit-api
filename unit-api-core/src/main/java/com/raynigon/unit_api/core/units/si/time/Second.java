package com.raynigon.unit_api.core.units.si.time;

import com.raynigon.unit_api.core.units.general.BaseUnit;
import com.raynigon.unit_api.core.units.si.SISystem;
import tech.units.indriya.unit.UnitDimension;

import javax.measure.quantity.Time;

public class Second extends BaseUnit<Time> {

    public Second() {
        super(SISystem.ID, "s", "Second", Time.class, UnitDimension.TIME);
    }

}
