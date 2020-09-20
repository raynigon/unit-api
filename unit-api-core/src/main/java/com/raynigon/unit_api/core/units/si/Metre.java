package com.raynigon.unit_api.core.units.si;

import com.raynigon.unit_api.core.units.general.BaseUnit;
import tech.units.indriya.unit.UnitDimension;

import javax.measure.quantity.Length;

public class Metre extends BaseUnit<Length> {

    public Metre() {
        super("m", "Metre", UnitDimension.LENGTH);
    }
}
