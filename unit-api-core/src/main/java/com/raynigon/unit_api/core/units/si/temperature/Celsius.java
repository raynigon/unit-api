package com.raynigon.unit_api.core.units.si.temperature;

import com.raynigon.unit_api.core.units.general.AlternateUnit;
import com.raynigon.unit_api.core.units.general.IUnit;
import com.raynigon.unit_api.core.units.general.TransformedUnit;
import com.raynigon.unit_api.core.units.si.SISystem;
import com.raynigon.unit_api.core.units.si.length.Kilometre;
import com.raynigon.unit_api.core.units.si.speed.MetrePerSecond;
import com.raynigon.unit_api.core.units.si.time.Hour;
import tech.units.indriya.function.MultiplyConverter;

import javax.measure.Unit;
import javax.measure.quantity.Speed;
import javax.measure.quantity.Temperature;

public class Celsius extends TransformedUnit<Temperature> implements IUnit<Temperature> {

    @SuppressWarnings("unchecked")
    public Celsius() {
        super(
                "\u2103",
                "Celsius",
                new Kelvin().shift(273.15),
                new Kelvin(),
                MultiplyConverter.of(1)
        );
    }

    @Override
    public String getSystemId() {
        return SISystem.ID;
    }

    @Override
    public Class<Temperature> getQuantityType() {
        return Temperature.class;
    }
}
