package com.raynigon.unit.api.core.units.si.volume;

import com.raynigon.unit.api.core.units.si.SISystem;
import com.raynigon.unit.api.core.units.si.length.Metre;
import com.raynigon.unit.api.core.units.general.AlternateUnit;

import javax.measure.Unit;
import javax.measure.quantity.Volume;

public class CubicMetre extends AlternateUnit<Volume> {

    @SuppressWarnings("unchecked")
    public CubicMetre() {
        super(SISystem.ID, "m³", "Cubic Metre", (Unit<Volume>) new Metre().pow(3), Volume.class);
    }
}