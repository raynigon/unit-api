package com.raynigon.unit.api.jackson.helpers


import com.raynigon.unit.api.core.units.si.dimensionless.Percent
import com.raynigon.unit.api.core.units.si.temperature.Celsius
import com.raynigon.unit.api.jackson.annotation.JsonQuantityReader

import javax.measure.Quantity
import javax.measure.quantity.Dimensionless
import javax.measure.quantity.Temperature

class WeatherEntity {

    public WeatherEntity() {}

    public WeatherEntity(Quantity<Temperature> temperature, Quantity<Dimensionless> humidity) {
        this.temperature = temperature
        this.humidity = humidity
    }

    @com.raynigon.unit.api.jackson.annotation.JsonUnit(unit = Celsius)
    @JsonQuantityReader(CelsiusReader.class)
    public Quantity<Temperature> temperature

    @com.raynigon.unit.api.jackson.annotation.JsonUnit(unit = Percent)
    @com.raynigon.unit.api.jackson.annotation.JsonQuantityWriter(PercentWriter.class)
    public Quantity<Dimensionless> humidity
}
