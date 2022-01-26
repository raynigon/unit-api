package com.raynigon.unit_api.jackson.helpers


import com.raynigon.unit_api.core.units.si.dimensionless.Percent
import com.raynigon.unit_api.core.units.si.temperature.Celsius
import com.raynigon.unit_api.jackson.annotation.JsonQuantityReader
import com.raynigon.unit_api.jackson.annotation.JsonQuantityWriter
import com.raynigon.unit_api.jackson.annotation.JsonUnit

import javax.measure.Quantity
import javax.measure.quantity.Dimensionless
import javax.measure.quantity.Temperature

class WeatherEntity {

    public WeatherEntity() {}

    public WeatherEntity(Quantity<Temperature> temperature, Quantity<Dimensionless> humidity) {
        this.temperature = temperature
        this.humidity = humidity
    }

    @JsonUnit(unit = Celsius)
    @JsonQuantityReader(CelsiusReader.class)
    public Quantity<Temperature> temperature

    @JsonUnit(unit = Percent)
    @JsonQuantityWriter(PercentWriter.class)
    public Quantity<Dimensionless> humidity
}
