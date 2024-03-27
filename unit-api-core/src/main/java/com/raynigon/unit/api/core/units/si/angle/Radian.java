package com.raynigon.unit.api.core.units.si.angle;

import com.raynigon.unit.api.core.units.general.BaseUnit;
import com.raynigon.unit.api.core.units.general.UnitDimension;
import com.raynigon.unit.api.core.units.si.SISystem;

import javax.measure.quantity.Angle;
import javax.measure.quantity.SolidAngle;

public class Radian extends BaseUnit<Angle> {

    public Radian() {
        super(
                SISystem.ID,
                "rad",
                "Radian",
                Angle.class,
                UnitDimension.NONE
        );
    }
}