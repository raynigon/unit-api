package com.raynigon.unit_api.jackson.helpers

import com.github.raynigon.unit_api_starter.jpa.annotation.JpaUnit
import com.raynigon.unit_api.core.annotation.QuantityShape
import org.hibernate.annotations.Type

import javax.measure.Quantity
import javax.measure.quantity.Length
import javax.measure.quantity.Speed
import javax.measure.quantity.Temperature
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "basic_entity")
class BasicEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "VARCHAR(255)")
    public String id

    @JpaUnit(unit = "km/h")
    @Column(name = "speed", nullable = false, columnDefinition = "SMALLINT")
    @Type(type = "com.github.raynigon.unit_api_starter.jpa.type.QuantityType")
    public Quantity<Speed> speed

    @JpaUnit(unit = "℃", shape = QuantityShape.STRING)
    @Column(name = "temperature", nullable = false, columnDefinition = "REAL")
    @Type(type = "com.github.raynigon.unit_api_starter.jpa.type.QuantityType")
    public Quantity<Temperature> temperature

    @JpaUnit(unit = "km")
    @Column(name = "distance", nullable = false, columnDefinition = "INT")
    @Type(type = "com.github.raynigon.unit_api_starter.jpa.type.QuantityType")
    public Quantity<Length> distance
}
