package com.raynigon.unit.api.jpa.helpers

import com.raynigon.unit.api.core.annotation.QuantityShape
import com.raynigon.unit.api.core.units.si.length.Kilometre
import com.raynigon.unit.api.core.units.si.speed.KilometrePerHour
import com.raynigon.unit.api.core.units.si.temperature.Celsius
import com.raynigon.unit.api.jpa.type.QuantityType
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
@TypeDef(
        name = "quantity",
        typeClass = QuantityType.class,
        defaultForType = Quantity.class
)
class BasicEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "VARCHAR(255)")
    public String id

    @com.raynigon.unit.api.jpa.annotation.JpaUnit(unit = KilometrePerHour)
    @Column(name = "speed", nullable = false, columnDefinition = "SMALLINT")
    public Quantity<Speed> speed

    @com.raynigon.unit.api.jpa.annotation.JpaUnit(unit = Celsius, shape = QuantityShape.STRING)
    @Column(name = "temperature", nullable = false, columnDefinition = "REAL")
    public Quantity<Temperature> temperature

    @com.raynigon.unit.api.jpa.annotation.JpaUnit(unit = Kilometre)
    @Column(name = "distance", nullable = false, columnDefinition = "INT")
    public Quantity<Length> distance
}
