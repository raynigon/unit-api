package com.raynigon.unit.api.core.units.binary.bytes;

import com.raynigon.unit.api.core.function.unitconverter.MultiplyConverter;
import com.raynigon.unit.api.core.units.general.TransformedUnit;

import javax.measure.quantity.Dimensionless;

public class Bit extends TransformedUnit<Dimensionless> {
    public Bit() {
        super("bit", "Bit", new Byte(), MultiplyConverter.ofRational(1, 8));
    }
}
