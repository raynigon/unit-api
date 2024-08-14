package com.raynigon.unit.api.core.units.binary.bytes;

import com.raynigon.unit.api.core.units.binary.Data;
import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.BinaryPrefix;

public class Tebibyte extends ScaledUnit<Data> {
    public Tebibyte() {
        super(BinaryPrefix.TEBI, new Byte(), "Tebibyte");
    }
}
