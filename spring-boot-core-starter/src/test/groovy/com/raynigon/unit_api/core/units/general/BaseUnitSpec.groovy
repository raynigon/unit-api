package com.raynigon.unit_api.core.units.general

import spock.lang.Specification

import javax.measure.Dimension
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
