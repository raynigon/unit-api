package com.raynigon.unit_api.jackson.helpers

import com.raynigon.unit_api.core.annotation.QuantityShape
import com.raynigon.unit_api.jackson.annotation.JsonUnit

import javax.measure.Quantity
import javax.measure.quantity.Length
import javax.measure.quantity.Speed
import javax.measure.quantity.Temperature

class BasicEntity {

    public String id

    @JsonUnit(unit = "km/h")
    public Quantity<Speed> speed

    @JsonUnit(unit = "℃", shape = QuantityShape.STRING)
    public Quantity<Temperature> temperature

    @JsonUnit(unit = "km")
    public Quantity<Length> distance

    /*@JsonUnit(unit="mWh")
    public Quantity<Energy> energy*/
}
