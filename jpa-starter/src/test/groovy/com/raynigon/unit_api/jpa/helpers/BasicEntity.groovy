package com.raynigon.unit_api.jpa.helpers

import com.raynigon.unit_api.jpa.annotation.JpaUnit
import com.raynigon.unit_api.core.annotation.QuantityShape
import com.raynigon.unit_api.jpa.type.QuantityType
import org.hibernate.annotations.Parameter
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef

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
    public Quantity<Speed> speed

    @JpaUnit(unit = "℃", shape = QuantityShape.STRING)
    @Column(name = "temperature", nullable = false, columnDefinition = "REAL")
    public Quantity<Temperature> temperature

    @JpaUnit(unit = "km")
    @Column(name = "distance", nullable = false, columnDefinition = "INT")
    public Quantity<Length> distance
}
