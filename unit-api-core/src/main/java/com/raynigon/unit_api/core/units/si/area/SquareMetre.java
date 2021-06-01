package com.raynigon.unit_api.core.units.si.area;

import com.raynigon.unit_api.core.units.general.AlternateUnit;
import com.raynigon.unit_api.core.units.si.SISystem;
import com.raynigon.unit_api.core.units.si.length.Metre;

import javax.measure.Unit;
import javax.measure.quantity.Area;

public class SquareMetre extends AlternateUnit<Area> {

    @SuppressWarnings("unchecked")
    public SquareMetre() {
        super(SISystem.ID, "m²", "Squared Metre", (Unit<Area>) new Metre().pow(2), Area.class);
    }
}
