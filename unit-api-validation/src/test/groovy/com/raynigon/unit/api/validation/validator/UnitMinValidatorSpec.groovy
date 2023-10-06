package com.raynigon.unit.api.validation.validator

import com.raynigon.unit.api.core.units.si.SISystemUnitsConstants
import com.raynigon.unit.api.core.units.si.power.Watt
import com.raynigon.unit.api.validation.annotation.UnitMax
import com.raynigon.unit.api.validation.annotation.UnitMin
import jakarta.validation.Validation
import jakarta.validation.Validator
import spock.lang.Specification

import javax.measure.Quantity
import javax.measure.quantity.Power

class UnitMinValidatorSpec extends Specification {

    Validator validator

    def setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator()
    }

    def "validate unit max with value=#input"() {
        given:
        DummyEntity entity = new DummyEntity(SISystemUnitsConstants.Watt(input))

        when:
        def result = validator.validate(entity)

        then:
        result.size() == expected

        where:
        input | expected
        1     | 1
        8     | 1
        9     | 0
        10    | 0
    }

    static class DummyEntity {

        @UnitMin(value = 9.0, unit = Watt.class)
        Quantity<Power> power

        DummyEntity(Quantity<Power> power) {
            this.power = power
        }
    }
}

