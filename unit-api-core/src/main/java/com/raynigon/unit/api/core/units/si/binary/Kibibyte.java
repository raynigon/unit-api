package com.raynigon.unit.api.core.units.si.binary;

import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.BinaryPrefix;
import javax.measure.quantity.Dimensionless;

public class Kibibyte extends ScaledUnit<Dimensionless> {
    public Kibibyte() {
        super(BinaryPrefix.KIBI, new Byte(), "Kibibyte");
    }
}
