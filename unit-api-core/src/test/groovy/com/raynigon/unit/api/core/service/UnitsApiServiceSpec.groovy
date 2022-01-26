package com.raynigon.unit.api.core.service


import spock.lang.Specification

import javax.measure.Unit
import javax.measure.quantity.Energy

import static UnitsApiService.quantity

class UnitsApiServiceSpec extends Specification {

    def 'initialise works'() {
        expect:
        new DefaultUnitsApiService()
    }

    def 'joule to watt-hour works'() {
        given:
        def joule1 = quantity(3600, new com.raynigon.unit.api.core.units.si.energy.Joule())

        when:
        def result = joule1.to(UnitsApiService.getInstance().getUnit("Wh") as Unit<Energy>)

        then:
        result.value.intValue() == 1
    }

    def 'resolve m/s2'() {
        expect:
        UnitsApiService.getInstance().getUnit("m/s²") != null
    }

    def 'service loader works'(){
        expect:
        UnitsApiService.getInstance().getUnit("WTF") == new com.raynigon.unit.api.core.units.si.length.Metre()
    }
}
