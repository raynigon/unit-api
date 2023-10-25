package com.raynigon.unit.api.validation.validator

import com.raynigon.unit.api.core.units.si.SISystemUnitsConstants
import com.raynigon.unit.api.core.units.si.power.Watt
import com.raynigon.unit.api.validation.annotation.UnitMax
import jakarta.validation.Validation
import jakarta.validation.Validator
import spock.lang.Specification

import javax.measure.Quantity
import javax.measure.quantity.Power

class UnitMaxValidatorSpec extends Specification {

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
        1     | 0
        9     | 0
        10    | 1
        11    | 1
    }

    def "validate message is correct"() {
        given:
        DummyEntity entity = new DummyEntity(SISystemUnitsConstants.Watt(10))

        when:
        def result = validator.validate(entity)

        then:
        result.size() == 1
        result.first().message == "must be less than or equal to 9.0 class com.raynigon.unit.api.core.units.si.power.Watt"
    }


    static class DummyEntity {

        @UnitMax(value = 9.0, unit = Watt.class)
        Quantity<Power> power

        DummyEntity(Quantity<Power> power) {
            this.power = power
        }
    }
}

