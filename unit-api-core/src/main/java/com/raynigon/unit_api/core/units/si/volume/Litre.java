package com.raynigon.unit_api.core.units.si.volume;

import com.raynigon.unit_api.core.function.unitconverter.MultiplyConverter;
import com.raynigon.unit_api.core.units.general.IUnit;
import com.raynigon.unit_api.core.units.general.TransformedUnit;
import com.raynigon.unit_api.core.units.si.SISystem;

import javax.measure.quantity.Volume;

public class Litre extends TransformedUnit<Volume> implements IUnit<Volume> {

    @SuppressWarnings("unchecked")
    public Litre() {
        super(
                "l",
                "litre",
                new CubicMetre(),
                new CubicMetre(),
                MultiplyConverter.ofRational(1, 1000));
    }

    @Override
    public String getSystemId() {
        return SISystem.ID;
    }

    @Override
    public Class<Volume> getQuantityType() {
        return Volume.class;
    }
}
