package com.raynigon.unit.api.core.units.si.torque;

import com.raynigon.unit.api.core.quantities.Torque;
import com.raynigon.unit.api.core.units.general.AlternateUnit;
import com.raynigon.unit.api.core.units.si.SISystem;
import com.raynigon.unit.api.core.units.si.force.Newton;
import com.raynigon.unit.api.core.units.si.length.Metre;

import javax.measure.Unit;

public class NewtonMetre extends AlternateUnit<Torque> {

    @SuppressWarnings("unchecked")
    public NewtonMetre() {
        super(SISystem.ID, "N m", "Newton Metre", (Unit<Torque>) new Newton().multiply(new Metre()), Torque.class);
    }

    @Override
    public String[] getSymbolAliases() {
        return new String[]{
                "N⋅m", // UTF-8 Middle Dot
                "N·m"  // UTF-8 Interpunct
        };
    }
}