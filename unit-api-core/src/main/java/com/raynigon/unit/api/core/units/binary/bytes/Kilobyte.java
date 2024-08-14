package com.raynigon.unit.api.core.units.binary.bytes;

import com.raynigon.unit.api.core.units.binary.Data;
import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.MetricPrefix;

public class Kilobyte extends ScaledUnit<Data> {
    public Kilobyte() {
        super(MetricPrefix.KILO, new Byte(), "Kilobyte");
    }
}
