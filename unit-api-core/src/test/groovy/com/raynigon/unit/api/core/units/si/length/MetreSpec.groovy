package com.raynigon.unit.api.core.units.si.length

import spock.lang.Specification

import javax.measure.Quantity
import javax.measure.quantity.Length

import static com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.Metre

class MetreSpec extends Specification {

    def "create metre instance"() {
        when:
        // create_metre_quantity {
        Quantity<Length> distance = Metre(1)
        // }

        then:
        distance.value == 1
        distance.getUnit() == new Metre()
        distance < Metre(2)
    }
}
