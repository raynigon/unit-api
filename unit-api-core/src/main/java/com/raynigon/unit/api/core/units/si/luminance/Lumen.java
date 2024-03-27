package com.raynigon.unit.api.core.units.si.luminance;

import com.raynigon.unit.api.core.units.general.AlternateUnit;
import com.raynigon.unit.api.core.units.general.BaseUnit;
import com.raynigon.unit.api.core.units.general.UnitDimension;
import com.raynigon.unit.api.core.units.si.SISystem;
import com.raynigon.unit.api.core.units.si.angle.Steradian;

import javax.measure.Unit;
import javax.measure.quantity.Illuminance;
import javax.measure.quantity.LuminousFlux;

public class Lumen extends AlternateUnit<LuminousFlux> {

    @SuppressWarnings("unchecked")
    public Lumen() {
        super(
                SISystem.ID,
                "lm",
                "Lumen",
                (Unit<LuminousFlux>) new Candela().multiply(new Steradian()),
                LuminousFlux.class
        );
    }
}
