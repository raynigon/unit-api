package com.raynigon.unit.api.core.quantities


import spock.lang.Specification
import spock.lang.Unroll

class NumberQuantitySpec extends Specification {


    @Unroll
    def 'abs() should return #expectedValue#resultUnit for #value#unit'() {
        given:
        ComparableQuantity quantity = new NumberQuantity(value, unit)

        when:
        ComparableQuantity result = quantity.abs().to(resultUnit)

        then:
        result.value == expectedValue

        where:
        value | unit          | resultUnit      | expectedValue
        10    | new com.raynigon.unit.api.core.units.si.temperature.Celsius()       | new com.raynigon.unit.api.core.units.si.temperature.Celsius()            | 10
        -10   | new com.raynigon.unit.api.core.units.si.temperature.Celsius()       | new com.raynigon.unit.api.core.units.si.temperature.Celsius()            | 10
        -10   | new com.raynigon.unit.api.core.units.si.temperature.Celsius()       | new com.raynigon.unit.api.core.units.si.temperature.Kelvin()             | 283.15
        5     | new com.raynigon.unit.api.core.units.si.electrical.potential.Volt() | new com.raynigon.unit.api.core.units.si.electrical.potential.Volt()      | 5
        -5    | new com.raynigon.unit.api.core.units.si.electrical.potential.Volt() | new com.raynigon.unit.api.core.units.si.electrical.potential.Volt()      | 5
        -5    | new com.raynigon.unit.api.core.units.si.electrical.potential.Volt() | new com.raynigon.unit.api.core.units.si.electrical.potential.MilliVolt() | 5000
    }
}
