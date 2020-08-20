package com.raynigon.unit_api.core.service

import spock.lang.Specification
import tech.units.indriya.quantity.Quantities
import tech.units.indriya.unit.Units

import javax.measure.Unit
import javax.measure.quantity.Energy

class UnitResolverServiceSpec extends Specification {

    def 'initialise works'() {
        expect:
        new UnitResolverService()
    }

    def 'joule to watt-hour works'() {
        given:
        def joule1 = Quantities.getQuantity(3600, Units.JOULE)

        when:
        def result = joule1.to(UnitResolverService.getInstance().getUnit("Wh") as Unit<Energy>)

        then:
        result.value.intValue() == 1
    }
}
