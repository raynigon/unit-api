package com.raynigon.unit.api.jackson.helpers

import com.raynigon.unit.api.core.units.si.speed.KilometrePerHour
import com.raynigon.unit.api.jackson.annotation.JsonUnit

import javax.measure.Quantity
import javax.measure.quantity.Speed

class ExampleEntity {

    public String id;

    @JsonUnit(KilometrePerHour.class)
    public Quantity<Speed> speed;
}
