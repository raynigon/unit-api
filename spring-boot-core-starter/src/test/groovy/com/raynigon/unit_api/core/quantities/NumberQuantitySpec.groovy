package com.raynigon.unit_api.core.quantities

import com.raynigon.unit_api.core.units.si.electrical.potential.MilliVolt
import com.raynigon.unit_api.core.units.si.electrical.potential.Volt
import com.raynigon.unit_api.core.units.si.temperature.Celsius
import com.raynigon.unit_api.core.units.si.temperature.Kelvin
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
        10    | new Celsius() | new Celsius()   | 10
        -10   | new Celsius() | new Celsius()   | 10
        -10   | new Celsius() | new Kelvin()    | 283.15
        5     | new Volt()    | new Volt()      | 5
        -5    | new Volt()    | new Volt()      | 5
        -5    | new Volt()    | new MilliVolt() | 5000
    }
}
