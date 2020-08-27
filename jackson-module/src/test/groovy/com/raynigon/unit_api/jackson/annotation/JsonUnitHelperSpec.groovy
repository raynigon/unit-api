package com.raynigon.unit_api.jackson.annotation

import com.raynigon.unit_api.core.annotation.QuantityShape
import spock.lang.Specification
import spock.lang.Unroll

class JsonUnitHelperSpec extends Specification {

    def 'instantiate class'() {
        expect:
        new JsonUnitHelper() != null
    }

    @Unroll
    def 'determine shape #source'() {

        given:
        def jpaUnit = Mock(JsonUnit)

        when:
        def result = JsonUnitHelper.getShape(jpaUnit)

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
        JsonUnitHelper.getShape(null) == QuantityShape.NUMBER
    }

    def 'determine unit name'() {

        given:
        def jpaUnit = Mock(JsonUnit)
        jpaUnit.unit() >> unit
        jpaUnit.value() >> value

        when:
        def result = JsonUnitHelper.getUnitName(jpaUnit)

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
        JsonUnitHelper.getUnitName(null) == null
    }
}
