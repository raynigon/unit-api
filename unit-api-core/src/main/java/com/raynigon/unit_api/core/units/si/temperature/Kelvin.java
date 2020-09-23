package com.raynigon.unit_api.core.units.si.temperature;

import com.raynigon.unit_api.core.units.general.BaseUnit;
import com.raynigon.unit_api.core.units.general.UnitDimension;
import com.raynigon.unit_api.core.units.si.SISystem;

import javax.measure.quantity.Temperature;

public class Kelvin extends BaseUnit<Temperature> {


    public Kelvin() {
        super(SISystem.ID, "K", "Kelvin", Temperature.class, UnitDimension.TEMPERATURE);
    }
}