package com.raynigon.unit.api.core.units.binary.bytes;

import com.raynigon.unit.api.core.units.binary.Data;
import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.MetricPrefix;

public class Gigabyte extends ScaledUnit<Data> {
    public Gigabyte() {
        super(MetricPrefix.GIGA, new Byte(), "Gigabyte");
    }
}
