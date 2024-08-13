package com.raynigon.unit.api.core.units.si.binary;

import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.BinaryPrefix;
import javax.measure.quantity.Dimensionless;

public class Gibibyte extends ScaledUnit<Dimensionless> {
    public Gibibyte() {
        super(BinaryPrefix.GIBI, new Byte(), "Gibibyte");
    }
}
