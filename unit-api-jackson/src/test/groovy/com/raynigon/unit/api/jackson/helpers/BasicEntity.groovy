package com.raynigon.unit.api.jackson.helpers

import com.raynigon.unit.api.core.annotation.QuantityShape
import com.raynigon.unit.api.core.units.si.length.Kilometre
import com.raynigon.unit.api.core.units.si.speed.KilometrePerHour
import com.raynigon.unit.api.core.units.si.temperature.Celsius

import javax.measure.Quantity
import javax.measure.quantity.Length
import javax.measure.quantity.Speed
import javax.measure.quantity.Temperature

class BasicEntity {

    public String id;

    @com.raynigon.unit.api.jackson.annotation.JsonUnit(unit = KilometrePerHour)
    public Quantity<Speed> speed;

    @com.raynigon.unit.api.jackson.annotation.JsonUnit(unit = Celsius, shape = QuantityShape.STRING)
    public Quantity<Temperature> temperature;

    @com.raynigon.unit.api.jackson.annotation.JsonUnit(unit = Kilometre)
    public Quantity<Length> distance;

}
