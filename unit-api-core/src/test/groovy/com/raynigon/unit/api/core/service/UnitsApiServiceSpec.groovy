package com.raynigon.unit.api.core.service

import com.raynigon.unit.api.core.service.testdata.DummySystemOfUnits
import com.raynigon.unit.api.core.service.testdata.DummyUnit
import com.raynigon.unit.api.core.units.si.energy.Joule
import com.raynigon.unit.api.core.units.si.length.Metre
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
        UnitsApiService.getInstance().getUnit("WTF") == new DummyUnit()
    }

    def 'add SystemOfUnits'(){
        expect:
        // add_system_of_units {
        UnitsApiService.getInstance().addSystemOfUnits(new DummySystemOfUnits())
        // }
    }
}
