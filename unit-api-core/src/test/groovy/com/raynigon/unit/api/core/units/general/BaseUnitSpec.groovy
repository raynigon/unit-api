package com.raynigon.unit.api.core.units.general

import spock.lang.Specification

import javax.measure.Quantity

class BaseUnitSpec extends Specification {

    def 'getBaseUnits returns null'() {
        given:
        BaseUnit baseUnit = new BaseUnit("-", ".-.", "-", Quantity.class, UnitDimension.LENGTH)

        when:
        def result = baseUnit.getBaseUnits()

        then:
        result == null
    }
}
