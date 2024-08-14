package com.raynigon.unit.api.core.units.binary.bytes;

import com.raynigon.unit.api.core.units.binary.BinarySystem;
import com.raynigon.unit.api.core.units.binary.Data;
import com.raynigon.unit.api.core.units.general.BaseUnit;
import com.raynigon.unit.api.core.units.general.UnitDimension;

public class Byte extends BaseUnit<Data> {
    public Byte() {
        super(BinarySystem.ID, "B", "Byte", Data.class, UnitDimension.DATA);
    }
}
