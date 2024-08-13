package com.raynigon.unit.api.core.units.si.binary;

import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.BinaryPrefix;
import javax.measure.quantity.Dimensionless;

public class Tebibyte extends ScaledUnit<Dimensionless> {
    public Tebibyte() {
        super(BinaryPrefix.TEBI, new Byte(), "Tebibyte");
    }
}
