package com.raynigon.unit.api.core.units.binary.bytes;

import com.raynigon.unit.api.core.units.binary.Data;
import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.BinaryPrefix;

public class Kibibyte extends ScaledUnit<Data> {
    public Kibibyte() {
        super(BinaryPrefix.KIBI, new Byte(), "Kibibyte");
    }
}
