package com.raynigon.unit_api.core.units.si.time;

import com.raynigon.unit_api.core.units.general.ScaledUnit;

import javax.measure.MetricPrefix;
import javax.measure.quantity.Time;

public class MicroSecond extends ScaledUnit<Time> {


    public MicroSecond() {
        super(MetricPrefix.MICRO, new Second(), "MicroSecond");
    }
}
