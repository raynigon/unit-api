package com.raynigon.unit_api.core.units.si.electrical.potential;

import com.raynigon.unit_api.core.units.general.AlternateUnit;
import com.raynigon.unit_api.core.units.general.ScaledUnit;
import com.raynigon.unit_api.core.units.si.SISystem;
import com.raynigon.unit_api.core.units.si.electrical.current.Ampere;
import com.raynigon.unit_api.core.units.si.power.Watt;

import javax.measure.MetricPrefix;
import javax.measure.Unit;
import javax.measure.quantity.ElectricPotential;

public class MilliVolt extends ScaledUnit<ElectricPotential> {

    public MilliVolt() {
        super(
                MetricPrefix.MILLI,
                new Volt(),
                "MilliVolt"
        );
    }
}
