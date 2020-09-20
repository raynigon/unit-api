package com.raynigon.unit_api.core.units.si.length;

import com.raynigon.unit_api.core.units.general.ScaledUnit;

import javax.measure.MetricPrefix;
import javax.measure.quantity.Length;

public class Kilometre extends ScaledUnit<Length> {

    public Kilometre() {
        super(MetricPrefix.KILO, new Metre(), "Kilometre");
    }
}
