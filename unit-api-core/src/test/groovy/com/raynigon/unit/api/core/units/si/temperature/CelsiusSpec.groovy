package com.raynigon.unit.api.core.units.si.temperature


import spock.lang.Specification

import javax.measure.Quantity
import javax.measure.quantity.Temperature

import static com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.Celsius

class CelsiusSpec extends Specification {

    def "create celsius instance"() {
        when:
        // create_celsius_quantity {
        Quantity<Temperature> temperature = Celsius(1)
        // }

        then:
        temperature.value == 1
        temperature.getUnit() == new Celsius()
        temperature < Celsius(2)
    }

    def "calculate with celsius instance"() {
        given:
        // calculate_with_temperature {
        Quantity<Temperature> temperature0 = Celsius(1)
        Quantity<Temperature> temperature1 = Celsius(2)
        // }
        when:
        // calculate_with_temperature {
        Quantity<Temperature> result = temperature0.add(temperature1)
        // }
        then:
        result > temperature0
        result > temperature1
        result > Celsius(3)
        result < Celsius(300)
        // calculate_with_temperature {
        result == Celsius(276.15)
        //}
    }
}
