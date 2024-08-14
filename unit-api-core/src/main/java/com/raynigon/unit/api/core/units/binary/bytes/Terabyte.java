package com.raynigon.unit.api.core.units.binary.bytes;

import com.raynigon.unit.api.core.units.binary.Data;
import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.MetricPrefix;

public class Terabyte extends ScaledUnit<Data> {
    public Terabyte() {
        super(MetricPrefix.TERA, new Byte(), "Terabyte");
    }
}
