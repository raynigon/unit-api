package com.raynigon.unit_api.jpa.annotation

import com.raynigon.unit_api.core.annotation.QuantityShape
import spock.lang.Specification
import spock.lang.Unroll

import javax.measure.quantity.Speed

class JpaUnitHelperSpec extends Specification {

    @Unroll
    def 'determine shape #source'() {

        given:
        def jpaUnit = Mock(JpaUnit)

        when:
        def result = JpaUnitHelper.getShape(jpaUnit)

        then:
        1 * jpaUnit.shape() >> source

        and:
        expectedResult == result

        where:
        source               | expectedResult
        QuantityShape.NUMBER | QuantityShape.NUMBER
        QuantityShape.STRING | QuantityShape.STRING
    }

    def 'determine shape with null parameter'() {

        expect:
        JpaUnitHelper.getShape(null) == QuantityShape.NUMBER
    }

    def 'determine unit name'() {

        given:
        def jpaUnit = Mock(JpaUnit)
        jpaUnit.unit() >> unit
        jpaUnit.value() >> value

        when:
        def result = JpaUnitHelper.getUnitName(jpaUnit)

        then:
        expectedResult == result

        where:
        value | unit | expectedResult
        ""    | ""   | null
        "m"   | ""   | "m"
        ""    | "m"  | "m"
    }

    def 'determine unit name with null parameter'() {

        expect:
        JpaUnitHelper.getUnitName(null) == null
    }

    def 'determine quantityType'() {

        given:
        def jpaUnit = Mock(JpaUnit)
        jpaUnit.quantityType() >> source

        when:
        def result = JpaUnitHelper.getQuantityType(jpaUnit)

        then:
        expectedResult == result

        where:
        source             | expectedResult
        NoneQuantity.class | null
        ArrayList.class    | null
        Speed.class        | Speed.class
    }

    def 'determine quantityType with null parameter'() {

        expect:
        JpaUnitHelper.getQuantityType(null) == null
    }
}
