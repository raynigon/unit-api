package com.raynigon.unit.api.core.service.testdata;

import com.raynigon.unit.api.core.units.general.AlternateUnit;
import com.raynigon.unit.api.core.units.si.length.Metre;

import javax.measure.quantity.Length;

public class DummyUnit extends AlternateUnit<Length> {

    public DummyUnit() {
        super("DummySystemOfUnits", "WTF", "DummyUnit", new Metre(), Length.class);
    }
}
