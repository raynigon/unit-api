package com.raynigon.unit.api.core.units.si.binary;

import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.MetricPrefix;
import javax.measure.quantity.Dimensionless;

public class Gigabyte extends ScaledUnit<Dimensionless> {
    public Gigabyte() {
        super(MetricPrefix.GIGA, new Byte(), "Gigabyte");
    }
}
