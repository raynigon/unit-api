package com.raynigon.unit.api.core.units.si.angle;

import com.raynigon.unit.api.core.units.general.BaseUnit;
import com.raynigon.unit.api.core.units.general.UnitDimension;
import com.raynigon.unit.api.core.units.si.SISystem;

import javax.measure.quantity.SolidAngle;

public class Steradian extends BaseUnit<SolidAngle> {

    public Steradian() {
        super(
                SISystem.ID,
                "sr",
                "Steradian",
                SolidAngle.class,
                UnitDimension.NONE
        );
    }
}