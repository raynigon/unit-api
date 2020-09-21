package com.raynigon.unit_api.core.units.si.time;

import com.raynigon.unit_api.core.units.general.ScaledUnit;

import javax.measure.quantity.Time;

public class Minute extends ScaledUnit<Time> {


    public Minute() {
        super(60, new Second(), "min", "Minute");
    }
}
