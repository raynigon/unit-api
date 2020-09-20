package com.raynigon.unit_api.core.units.si.temperature;

import com.raynigon.unit_api.core.units.general.BaseUnit;
import com.raynigon.unit_api.core.units.si.SISystem;
import tech.units.indriya.unit.UnitDimension;

import javax.measure.Dimension;
import javax.measure.quantity.Temperature;

public class Kelvin extends BaseUnit<Temperature> {


    public Kelvin() {
        super(SISystem.ID, "K", "Kelvin", Temperature.class, UnitDimension.TEMPERATURE);
    }
}