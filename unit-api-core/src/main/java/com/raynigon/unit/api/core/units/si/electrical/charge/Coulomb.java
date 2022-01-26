package com.raynigon.unit.api.core.units.si.electrical.charge;

import com.raynigon.unit.api.core.units.si.SISystem;
import com.raynigon.unit.api.core.units.si.time.Second;
import com.raynigon.unit.api.core.units.general.AlternateUnit;
import com.raynigon.unit.api.core.units.si.electrical.current.Ampere;

import javax.measure.Unit;
import javax.measure.quantity.ElectricCharge;

public class Coulomb extends AlternateUnit<ElectricCharge> {

    @SuppressWarnings("unchecked")
    public Coulomb() {
        super(SISystem.ID, "C", "Coulomb", (Unit<ElectricCharge>) new Second().multiply(new Ampere()), ElectricCharge.class);
    }
}
