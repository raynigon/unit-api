package com.raynigon.unit.api.core.units.general

import com.raynigon.unit.api.core.units.si.frequency.Hertz
import com.raynigon.unit.api.core.units.si.length.Metre
import com.raynigon.unit.api.core.units.si.power.Watt
import com.raynigon.unit.api.core.units.si.time.Hour
import com.raynigon.unit.api.core.units.si.time.Second
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static ProductUnit.*

class ProductUnitSpec extends Specification {

    @Shared
    ProductUnit wattSquared = ofPow(new Watt(), 2)

    def 'default product unit'() {
        when:
        new ProductUnit()

        then:
        noExceptionThrown()
    }

    def 'simple product unit'() {
        when:
        new ProductUnit(ofPow(new Watt(), 2) as ProductUnit<?>)

        then:
        noExceptionThrown()
    }

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
        def unit = ofProduct(new Watt(), new Watt())

        when:
        def result = unit.pow(2)

        then:
        result == ofPow(new Watt(), 4)
    }

    @Unroll
    def 'unit count should be #count'() {
        when:
        def result = (unit as ProductUnit).getUnitCount()

        then:
        result == count

        where:
        unit                                                                               | count
        ofPow(new Watt(), 2)                                                                                                                                                                                                | 1
        ofProduct(new Watt(), new Hour())                                                                                                                                          | 2
        ofProduct(ofProduct(new Watt(), new Hour()), ofProduct(new Metre(), new Hour()))       | 3
        ofProduct(ofProduct(new Watt(), new Hour()), ofProduct(new Metre(), new Second()))     | 4
        ofProduct(ofProduct(new Watt(), new Hour()), ofProduct(new Metre(), new Hertz())) | 4
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
        def unit = ofProduct(new Watt(), ofRoot(new Hour(), 2))

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
        ofPow(new Watt(), 2) | ofPow(new Watt(), 2) | true
        ofPow(new Watt(), 2) | new Watt()           | false
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
        ofPow(new Metre(), 2) | UnitDimension.LENGTH.pow(2)
        wattSquared           | UnitDimension.LENGTH.pow(4) * (UnitDimension.MASS.pow(2) * UnitDimension.TIME.pow(-6))
    }

    def 'toString returns adapted symbol'() {
        expect:
        wattSquared.toString() == "W^2"
    }
}
