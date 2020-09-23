package com.raynigon.unit_api.core.units.si.electrical.potential;

import com.raynigon.unit_api.core.units.general.AlternateUnit;
import com.raynigon.unit_api.core.units.si.SISystem;
import com.raynigon.unit_api.core.units.si.electrical.current.Ampere;
import com.raynigon.unit_api.core.units.si.power.Watt;

import javax.measure.Unit;
import javax.measure.quantity.ElectricPotential;

public class Volt extends AlternateUnit<ElectricPotential> {

    @SuppressWarnings("unchecked")
    public Volt() {
        super(
                SISystem.ID,
                "V",
                "Volt",
                (Unit<ElectricPotential>) new Watt().divide(new Ampere()),
                ElectricPotential.class
        );
    }
}
