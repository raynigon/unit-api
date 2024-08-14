package com.raynigon.unit.api.core.units.binary.bytes;

import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.MetricPrefix;
import javax.measure.quantity.Dimensionless;

public class Kilobyte extends ScaledUnit<Dimensionless> {
    public Kilobyte() {
        super(MetricPrefix.KILO, new Byte(), "Kilobyte");
    }
}
