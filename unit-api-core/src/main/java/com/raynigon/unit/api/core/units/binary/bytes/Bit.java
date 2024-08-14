package com.raynigon.unit.api.core.units.binary.bytes;

import com.raynigon.unit.api.core.function.unitconverter.MultiplyConverter;
import com.raynigon.unit.api.core.units.binary.Data;
import com.raynigon.unit.api.core.units.general.TransformedUnit;

public class Bit extends TransformedUnit<Data> {
    public Bit() {
        super("bit", "Bit", new Byte(), MultiplyConverter.ofRational(1, 8));
    }
}
