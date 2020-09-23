package com.raynigon.unit_api.core.units.si.power;

import com.raynigon.unit_api.core.units.general.ScaledUnit;
import com.raynigon.unit_api.core.units.si.length.Metre;

import javax.measure.MetricPrefix;
import javax.measure.quantity.Length;
import javax.measure.quantity.Power;

public class MilliWatt extends ScaledUnit<Power> {

    public MilliWatt() {
        super(MetricPrefix.MILLI, new Watt(), "MilliWatt");
    }
}
