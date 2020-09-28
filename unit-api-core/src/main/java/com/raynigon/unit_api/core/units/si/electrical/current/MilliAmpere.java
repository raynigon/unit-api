package com.raynigon.unit_api.core.units.si.electrical.current;

import com.raynigon.unit_api.core.units.general.ScaledUnit;
import com.raynigon.unit_api.core.units.si.electrical.potential.Volt;

import javax.measure.MetricPrefix;
import javax.measure.quantity.ElectricCurrent;
import javax.measure.quantity.ElectricPotential;

public class MilliAmpere extends ScaledUnit<ElectricCurrent> {

    public MilliAmpere() {
        super(
                MetricPrefix.MILLI,
                new Ampere(),
                "MilliAmpere"
        );
    }
}
