package com.raynigon.unit.api.jpa.helpers

import com.raynigon.unit.api.core.annotation.QuantityShape
import com.raynigon.unit.api.core.units.si.length.Kilometre
import com.raynigon.unit.api.core.units.si.mass.Kilogram
import com.raynigon.unit.api.core.units.si.power.Watt
import com.raynigon.unit.api.core.units.si.speed.KilometrePerHour
import com.raynigon.unit.api.core.units.si.temperature.Celsius
import com.raynigon.unit.api.jpa.annotation.JpaUnit
import com.raynigon.unit.api.jpa.type.QuantityType
import org.hibernate.annotations.Type

import javax.measure.Quantity
import javax.measure.quantity.Length
import javax.measure.quantity.Mass
import javax.measure.quantity.Power
import javax.measure.quantity.Speed
import javax.measure.quantity.Temperature
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "basic_entity")
class BasicEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "VARCHAR(255)")
    public String id

    @Type(QuantityType.class)
    @JpaUnit(unit = KilometrePerHour)
    @Column(name = "speed", nullable = false, columnDefinition = "SMALLINT")
    public Quantity<Speed> speed

    @Type(QuantityType.class)
    @JpaUnit(unit = Celsius, shape = QuantityShape.NUMERIC_STRING)
    @Column(name = "temperature", nullable = false, columnDefinition = "VARCHAR")
    public Quantity<Temperature> temperature

    @Type(QuantityType.class)
    @JpaUnit(unit = Kilometre)
    @Column(name = "distance", nullable = false, columnDefinition = "INT")
    public Quantity<Length> distance

    @Type(QuantityType.class)
    @JpaUnit(unit = Kilogram, shape = QuantityShape.STRING)
    @Column(name = "weight", nullable = true, columnDefinition = "VARCHAR")
    public Quantity<Mass> weight

    @Type(QuantityType.class)
    @JpaUnit(unit = Watt)
    @Column(name = "power", nullable = true, columnDefinition = "DOUBLE PRECISION")
    public Quantity<Power> power
}
