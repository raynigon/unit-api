package com.raynigon.unit_api.core.function

import com.raynigon.unit_api.core.quantities.NumberQuantity
import com.raynigon.unit_api.core.units.si.energy.Joule
import com.raynigon.unit_api.core.units.si.length.Kilometre
import com.raynigon.unit_api.core.units.si.length.Metre
import com.raynigon.unit_api.core.units.si.temperature.Celsius
import com.raynigon.unit_api.core.units.si.temperature.Kelvin
import spock.lang.Specification
import spock.lang.Unroll

import javax.measure.Quantity
import javax.measure.quantity.Length

import static com.raynigon.unit_api.core.units.si.SISystemUnitsConstants.*

class ScaleHelperSpec extends Specification {

    def "absolute quantities return true"() {

        when:
        def result = ScaleHelper.isAbsolute(quantity)

        then:
        result == expectedResult

        where:
        quantity                                                                | expectedResult
        Metre(1)                                                                | true
        new NumberQuantity<Length>(1, new Kilometre(), Quantity.Scale.RELATIVE) | false
    }

    def "relative quantities return true"() {

        when:
        def result = ScaleHelper.isRelative(quantity)

        then:
        result == expectedResult

        where:
        quantity                                                                | expectedResult
        Metre(1)                                                                | false
        new NumberQuantity<Length>(1, new Kilometre(), Quantity.Scale.RELATIVE) | true
    }

    @Unroll
    def "convert #unit to #targetUnit"() {

        given:
        def quantity = new NumberQuantity<Length>(value, unit, scale)

        when:
        def result = ScaleHelper.convertTo(quantity, targetUnit)

        then:
        result == expectedQuantity

        where:
        value | unit            | scale                   | targetUnit   | expectedQuantity
        1     | new Kilometre() | Quantity.Scale.RELATIVE | new Metre()  | Metre(1000)
        2     | new Celsius()   | Quantity.Scale.ABSOLUTE | new Kelvin() | Kelvin(275.15)
    }

    def "Addition"() {

        given:
        def operator = { a, b -> a.doubleValue() + b.doubleValue() }

        when:
        def result = ScaleHelper.addition(quantity1, quantity2, operator)

        then:
        result == expectedQuantity

        where:
        quantity1 | quantity2        | expectedQuantity
        Metre(1)  | Kilometre(0.001) | Metre(2)
    }

    def "ScalarMultiplication"() {
        given:
        def operator = { a -> a * scalar }

        when:
        def result = ScaleHelper.scalarMultiplication(quantity, operator)

        then:
        result == expectedQuantity

        where:
        quantity | scalar | expectedQuantity
        Metre(1) | 2      | Metre(2)
    }

    def "Multiplication"() {
        given:
        def operator = { a, b -> new Double(a.doubleValue() * b.doubleValue()) }
        def unitOperator = { a, b -> targetUnit }

        when:
        def result = ScaleHelper.multiplication(quantity1, quantity2, operator, unitOperator)

        then:
        result == expectedQuantity

        where:
        quantity1 | quantity2 | targetUnit  | expectedQuantity
        Newton(1) | Metre(1)  | new Joule() | Joule(1)
    }
}
