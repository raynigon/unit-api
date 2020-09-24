package com.raynigon.unit_api.core.units.si.length;

import com.raynigon.unit_api.core.units.general.BaseUnit;
import com.raynigon.unit_api.core.units.general.UnitDimension;
import com.raynigon.unit_api.core.units.si.SISystem;

import javax.measure.quantity.Length;

public class Metre extends BaseUnit<Length> {

    public Metre() {
        super(SISystem.ID,"m", "Metre",  Length.class, UnitDimension.LENGTH);
    }
}
