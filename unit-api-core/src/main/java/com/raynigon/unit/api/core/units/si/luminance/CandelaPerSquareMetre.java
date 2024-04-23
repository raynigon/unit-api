package com.raynigon.unit.api.core.units.si.luminance;

import com.raynigon.unit.api.core.quantities.Luminance;
import com.raynigon.unit.api.core.units.general.AlternateUnit;
import com.raynigon.unit.api.core.units.general.TransformedUnit;
import com.raynigon.unit.api.core.units.si.SISystem;
import com.raynigon.unit.api.core.units.si.area.SquareMetre;

import javax.measure.Unit;
import javax.measure.quantity.Illuminance;

public class CandelaPerSquareMetre extends AlternateUnit<Luminance> {

    @SuppressWarnings("unchecked")
    public CandelaPerSquareMetre() {
        super(
                SISystem.ID,
                "lx",
                "Lux",
                (Unit<Luminance>) (new Candela().divide(new SquareMetre())),
                Luminance.class
        );
    }
}
