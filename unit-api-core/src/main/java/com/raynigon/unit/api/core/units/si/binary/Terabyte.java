package com.raynigon.unit.api.core.units.si.binary;

import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.MetricPrefix;
import javax.measure.quantity.Dimensionless;

public class Terabyte extends ScaledUnit<Dimensionless> {
    public Terabyte() {
        super(MetricPrefix.TERA, new Byte(), "Terabyte");
    }
}
