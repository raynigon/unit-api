package com.raynigon.unit.api.core.units.si.power;

import com.raynigon.unit.api.core.units.si.SISystem;
import com.raynigon.unit.api.core.units.si.energy.Joule;
import com.raynigon.unit.api.core.units.si.time.Second;
import com.raynigon.unit.api.core.units.general.AlternateUnit;

import javax.measure.Unit;
import javax.measure.quantity.Power;

public class Watt extends AlternateUnit<Power> {

    @SuppressWarnings("unchecked")
    public Watt() {
        super(SISystem.ID, "W", "Watt", (Unit<Power>) new Joule().divide(new Second()), Power.class);
    }
}
