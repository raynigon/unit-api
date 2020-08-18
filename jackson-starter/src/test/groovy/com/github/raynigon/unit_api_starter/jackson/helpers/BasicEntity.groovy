package com.github.raynigon.unit_api_starter.jackson.helpers

import javax.measure.Quantity
import javax.measure.quantity.Speed
import javax.measure.quantity.Temperature

class BasicEntity {

    public String id

    public Quantity<Speed> speed

    public Quantity<Temperature> temperature
}
