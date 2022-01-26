package com.raynigon.unit.api.core.units.general


import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static ProductUnit.*

class ProductUnitSpec extends Specification {

    @Shared
    ProductUnit wattSquared = ofPow(new com.raynigon.unit.api.core.units.si.power.Watt(), 2)

    def 'default product unit'() {
        when:
        new ProductUnit()

        then:
        noExceptionThrown()
    }

    def 'simple product unit'() {
        when:
        new ProductUnit(ofPow(new com.raynigon.unit.api.core.units.si.power.Watt(), 2) as ProductUnit<?>)

        then:
        noExceptionThrown()
    }

    def 'product of simple units'() {
        when:
        def result = ofProduct(new com.raynigon.unit.api.core.units.si.power.Watt(), new com.raynigon.unit.api.core.units.si.time.Hour())

        then:
        result.baseUnits == [new com.raynigon.unit.api.core.units.si.power.Watt(): 1, new com.raynigon.unit.api.core.units.si.time.Hour(): 1]
    }

    def 'product of product units'() {
        given:
        def a = ofProduct(new com.raynigon.unit.api.core.units.si.power.Watt(), new com.raynigon.unit.api.core.units.si.time.Hour())
        def b = ofProduct(new com.raynigon.unit.api.core.units.si.power.Watt(), new com.raynigon.unit.api.core.units.si.time.Hour())
        when:
        def result = ofProduct(a, b)

        then:
        result.baseUnits == [new com.raynigon.unit.api.core.units.si.power.Watt(): 2, new com.raynigon.unit.api.core.units.si.time.Hour(): 2]
    }

    def 'quotient of simple units'() {
        when:
        def result = ofQuotient(new com.raynigon.unit.api.core.units.si.length.Metre(), new com.raynigon.unit.api.core.units.si.time.Second())

        then:
        result.baseUnits == [new com.raynigon.unit.api.core.units.si.length.Metre(): 1, new com.raynigon.unit.api.core.units.si.time.Second(): -1]
    }

    def 'quotient of product units'() {
        given:
        def a = ofProduct(new com.raynigon.unit.api.core.units.si.length.Metre(), new com.raynigon.unit.api.core.units.si.length.Metre())
        def b = ofProduct(new com.raynigon.unit.api.core.units.si.time.Second(), new com.raynigon.unit.api.core.units.si.time.Second())
        when:
        def result = ofQuotient(a, b)

        then:
        result.baseUnits == [new com.raynigon.unit.api.core.units.si.length.Metre(): 2, new com.raynigon.unit.api.core.units.si.time.Second(): -2]
    }

    def 'root of simple unit'() {
        when:
        def result = ofRoot(new com.raynigon.unit.api.core.units.si.power.Watt(), 2)

        then:
        result.baseUnits == [new com.raynigon.unit.api.core.units.si.power.Watt(): 1]
    }

    def 'root of product units'() {
        given:
        def a = ofProduct(new com.raynigon.unit.api.core.units.si.power.Watt(), new com.raynigon.unit.api.core.units.si.time.Hour())
        when:
        def result = ofRoot(a, 2)

        then:
        result.baseUnits == [new com.raynigon.unit.api.core.units.si.power.Watt(): 1, new com.raynigon.unit.api.core.units.si.time.Hour(): 1]
    }

    def 'pow of simple unit'() {
        when:
        def result = ofPow(new com.raynigon.unit.api.core.units.si.power.Watt(), 2)

        then:
        result.baseUnits == [new com.raynigon.unit.api.core.units.si.power.Watt(): 2]
    }

    def 'pow of product units'() {
        given:
        def a = ofProduct(new com.raynigon.unit.api.core.units.si.power.Watt(), new com.raynigon.unit.api.core.units.si.time.Hour())
        when:
        def result = ofPow(a, 2)

        then:
        result.baseUnits == [new com.raynigon.unit.api.core.units.si.power.Watt(): 2, new com.raynigon.unit.api.core.units.si.time.Hour(): 2]
    }

    def 'pow with simple unit'() {
        given:
        def unit = ofProduct(new com.raynigon.unit.api.core.units.si.power.Watt(), new com.raynigon.unit.api.core.units.si.power.Watt())

        when:
        def result = unit.pow(2)

        then:
        result == ofPow(new com.raynigon.unit.api.core.units.si.power.Watt(), 4)
    }

    @Unroll
    def 'unit count should be #count'() {
        when:
        def result = (unit as ProductUnit).getUnitCount()

        then:
        result == count

        where:
        unit                                                                               | count
        ofPow(new com.raynigon.unit.api.core.units.si.power.Watt(), 2)                                                                                                                                                                                                | 1
        ofProduct(new com.raynigon.unit.api.core.units.si.power.Watt(), new com.raynigon.unit.api.core.units.si.time.Hour())                                                                                                                                          | 2
        ofProduct(ofProduct(new com.raynigon.unit.api.core.units.si.power.Watt(), new com.raynigon.unit.api.core.units.si.time.Hour()), ofProduct(new com.raynigon.unit.api.core.units.si.length.Metre(), new com.raynigon.unit.api.core.units.si.time.Hour()))       | 3
        ofProduct(ofProduct(new com.raynigon.unit.api.core.units.si.power.Watt(), new com.raynigon.unit.api.core.units.si.time.Hour()), ofProduct(new com.raynigon.unit.api.core.units.si.length.Metre(), new com.raynigon.unit.api.core.units.si.time.Second()))     | 4
        ofProduct(ofProduct(new com.raynigon.unit.api.core.units.si.power.Watt(), new com.raynigon.unit.api.core.units.si.time.Hour()), ofProduct(new com.raynigon.unit.api.core.units.si.length.Metre(), new com.raynigon.unit.api.core.units.si.frequency.Hertz())) | 4
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
        given:
        def unit = ofProduct(new com.raynigon.unit.api.core.units.si.power.Watt(), ofRoot(new com.raynigon.unit.api.core.units.si.time.Hour(), 2))

        when:
        def result = (unit as ProductUnit).getUnitRoot(1)

        then:
        result == 2
    }

    @Ignore
    def 'base units should be...'() {
        expect:
        false
    }

    @Unroll
    def 'unit #a and unit #b returns #expected for equality check'() {
        given:
        false

        when:
        def result = (a as ProductUnit).equals(b)

        then:
        result == expected

        where:
        a                    | b                    | expected
        wattSquared          | wattSquared          | true
        ofPow(new com.raynigon.unit.api.core.units.si.power.Watt(), 2) | ofPow(new com.raynigon.unit.api.core.units.si.power.Watt(), 2) | true
        ofPow(new com.raynigon.unit.api.core.units.si.power.Watt(), 2) | new com.raynigon.unit.api.core.units.si.power.Watt()           | false
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

    @Unroll
    def 'unit #unit should have dimension #dimension'() {
        expect:
        unit.getDimension() == dimension

        where:
        unit                  | dimension
        ofPow(new com.raynigon.unit.api.core.units.si.length.Metre(), 2) | UnitDimension.LENGTH.pow(2)
        wattSquared           | UnitDimension.LENGTH.pow(4) * (UnitDimension.MASS.pow(2) * UnitDimension.TIME.pow(-6))
    }

    def 'toString returns adapted symbol'() {
        expect:
        wattSquared.toString() == "W^2"
    }
}
