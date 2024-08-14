package com.raynigon.unit.api.core.units.binary.bytes;

import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.MetricPrefix;
import javax.measure.quantity.Dimensionless;

public class Megabyte extends ScaledUnit<Dimensionless> {
    public Megabyte() {
        super(MetricPrefix.MEGA, new Byte(), "Megabyte");
    }
}
