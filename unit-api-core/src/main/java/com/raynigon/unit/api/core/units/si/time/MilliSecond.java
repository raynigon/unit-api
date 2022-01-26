package com.raynigon.unit.api.core.units.si.time;

import com.raynigon.unit.api.core.units.general.ScaledUnit;

import javax.measure.MetricPrefix;
import javax.measure.quantity.Time;

public class MilliSecond extends ScaledUnit<Time> {

    public MilliSecond() {
        super(MetricPrefix.MILLI, new Second(), "MilliSecond");
    }
}
