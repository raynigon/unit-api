package com.raynigon.unit_api.core.units.general

import com.raynigon.unit_api.core.units.si.dimensionless.One
import com.raynigon.unit_api.core.units.si.frequency.Hertz
import com.raynigon.unit_api.core.units.si.length.Metre
import com.raynigon.unit_api.core.units.si.power.Watt
import com.raynigon.unit_api.core.units.si.time.Hour
import com.raynigon.unit_api.core.units.si.time.Second
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

import static com.raynigon.unit_api.core.units.general.ProductUnit.*

class ProductUnitSpec extends Specification {

    def 'product of simple units'() {
        when:
        def result = ofProduct(new Watt(), new Hour())

        then:
        result.baseUnits == [new Watt(): 1, new Hour(): 1]
    }

    def 'product of product units'() {
        given:
        def a = ofProduct(new Watt(), new Hour())
        def b = ofProduct(new Watt(), new Hour())
        when:
        def result = ofProduct(a, b)

        then:
        result.baseUnits == [new Watt(): 2, new Hour(): 2]
    }

    def 'quotient of simple units'() {
        when:
        def result = ofQuotient(new Metre(), new Second())

        then:
        result.baseUnits == [new Metre(): 1, new Second(): -1]
    }

    def 'quotient of product units'() {
        given:
        def a = ofProduct(new Metre(), new Metre())
        def b = ofProduct(new Second(), new Second())
        when:
        def result = ofQuotient(a, b)

        then:
        result.baseUnits == [new Metre(): 2, new Second(): -2]
    }

    def 'root of simple unit'() {
        when:
        def result = ofRoot(new Watt(), 2)

        then:
        result.baseUnits == [new Watt(): 1]
    }

    def 'root of product units'() {
        given:
        def a = ofProduct(new Watt(), new Hour())
        when:
        def result = ofRoot(a, 2)

        then:
        result.baseUnits == [new Watt(): 1, new Hour(): 1]
    }

    def 'pow of simple unit'() {
        when:
        def result = ofPow(new Watt(), 2)

        then:
        result.baseUnits == [new Watt(): 2]
    }

    def 'pow of product units'() {
        given:
        def a = ofProduct(new Watt(), new Hour())
        when:
        def result = ofPow(a, 2)

        then:
        result.baseUnits == [new Watt(): 2, new Hour(): 2]
    }

    def 'pow with simple unit'() {
        given:
        def unit = ofProduct(new Watt(), new One())

        when:
        def result = unit.pow(2)

        then:
        result == ofPow(new Watt(), 2)
    }

    @Unroll
    def 'unit count should be #count'() {
        when:
        def result = (unit as ProductUnit).getUnitCount()

        then:
        result == count

        where:
        unit                                                                               | count
        ofPow(new Watt(), 2)                                                               | 1
        ofProduct(new Watt(), new Hour())                                                  | 2
        ofProduct(ofProduct(new Watt(), new Hour()), ofProduct(new Metre(), new Hour()))   | 3
        ofProduct(ofProduct(new Watt(), new Hour()), ofProduct(new Metre(), new Second())) | 4
        ofProduct(ofProduct(new Watt(), new Hour()), ofProduct(new Metre(), new Hertz()))  | 4
    }

    @Ignore
    def 'unit at index #index should be #unit'() {
        expect:
        false
    }

    @Ignore
    def 'unit pow at index #index should be #unit'() {
        expect:
        false
    }

    @Ignore
    def 'unit root at index #index should be #unit'() {
        expect:
        false
    }

    @Ignore
    def 'base units should be...'() {
        expect:
        false
    }

    @Ignore
    def 'simple unit should be converted to system unit'() {
        expect:
        false
    }

    @Ignore
    def 'simple unit should create valid system converted'() {
        expect:
        false
    }

    @Ignore
    def 'simple unit should have dimension #dimension'() {
        expect:
        false
    }
}
