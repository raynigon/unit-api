package com.raynigon.unit_api.core.function

import com.raynigon.unit_api.core.units.si.length.Kilometre
import spock.lang.Specification

import javax.measure.Unit

import static com.raynigon.unit_api.core.units.si.SISystemUnitsConstants.Kilometre
import static com.raynigon.unit_api.core.units.si.SISystemUnitsConstants.Metre

class QuantityConverterSpec extends Specification {

    def 'unit converter call works as expected'() {
        given:
        QuantityConverter converter = { Unit unit -> Metre(0).to(unit) }

        when:
        def result = converter.to(new Kilometre())

        then:
        result == Kilometre(0)
    }
}
