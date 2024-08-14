package com.raynigon.unit.api.core.units.binary.bytes;

import com.raynigon.unit.api.core.units.binary.Data;
import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.BinaryPrefix;

public class Mebibyte extends ScaledUnit<Data> {
    public Mebibyte() {
        super(BinaryPrefix.MEBI, new Byte(), "Mebibyte");
    }
}
