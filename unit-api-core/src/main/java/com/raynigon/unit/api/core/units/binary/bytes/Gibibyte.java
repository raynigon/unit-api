package com.raynigon.unit.api.core.units.binary.bytes;

import com.raynigon.unit.api.core.units.binary.Data;
import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.BinaryPrefix;

public class Gibibyte extends ScaledUnit<Data> {
    public Gibibyte() {
        super(BinaryPrefix.GIBI, new Byte(), "Gibibyte");
    }
}
