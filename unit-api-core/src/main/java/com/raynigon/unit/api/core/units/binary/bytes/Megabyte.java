package com.raynigon.unit.api.core.units.binary.bytes;

import com.raynigon.unit.api.core.units.binary.Data;
import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.MetricPrefix;

public class Megabyte extends ScaledUnit<Data> {
    public Megabyte() {
        super(MetricPrefix.MEGA, new Byte(), "Megabyte");
    }
}
