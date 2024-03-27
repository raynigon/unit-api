package com.raynigon.unit.api.core.units.si.luminance;

import com.raynigon.unit.api.core.units.general.AlternateUnit;
import com.raynigon.unit.api.core.units.si.SISystem;
import com.raynigon.unit.api.core.units.si.area.SquareMetre;

import javax.measure.Unit;
import javax.measure.quantity.Illuminance;

public class Lux extends AlternateUnit<Illuminance> {

    @SuppressWarnings("unchecked")
    public Lux() {
        super(
                SISystem.ID,
                "lx",
                "Lux",
                (Unit<Illuminance>) new Lumen().divide(new SquareMetre()),
                Illuminance.class
        );
    }
}
