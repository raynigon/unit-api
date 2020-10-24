package com.raynigon.unit_api.core.service

import com.raynigon.unit_api.core.units.si.energy.Joule
import com.raynigon.unit_api.core.units.si.length.Metre
import spock.lang.Specification

import javax.measure.Unit
import javax.measure.quantity.Energy

import static com.raynigon.unit_api.core.service.UnitsApiService.quantity

class UnitsApiServiceSpec extends Specification {

    def 'initialise works'() {
        expect:
        new DefaultUnitsApiService()
    }

    def 'joule to watt-hour works'() {
        given:
        def joule1 = quantity(3600, new Joule())

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
        UnitsApiService.getInstance().getUnit("WTF") == new Metre()
    }
}
