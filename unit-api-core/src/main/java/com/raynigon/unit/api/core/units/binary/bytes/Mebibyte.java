package com.raynigon.unit.api.core.units.binary.bytes;

import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.BinaryPrefix;
import javax.measure.quantity.Dimensionless;

public class Mebibyte extends ScaledUnit<Dimensionless> {
    public Mebibyte() {
        super(BinaryPrefix.MEBI, new Byte(), "Mebibyte");
    }
}
