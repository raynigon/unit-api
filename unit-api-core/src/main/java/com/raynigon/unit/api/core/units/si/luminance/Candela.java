package com.raynigon.unit.api.core.units.si.luminance;

import com.raynigon.unit.api.core.units.general.AlternateUnit;
import com.raynigon.unit.api.core.units.general.BaseUnit;
import com.raynigon.unit.api.core.units.general.UnitDimension;
import com.raynigon.unit.api.core.units.si.SISystem;
import com.raynigon.unit.api.core.units.si.area.SquareMetre;

import javax.measure.quantity.Illuminance;
import javax.measure.quantity.LuminousIntensity;

public class Candela extends BaseUnit<LuminousIntensity> {

    public Candela() {
        super(
                SISystem.ID,
                "cd",
                "Candela",
                LuminousIntensity.class,
                UnitDimension.LUMINOUS_INTENSITY
        );
    }
}
