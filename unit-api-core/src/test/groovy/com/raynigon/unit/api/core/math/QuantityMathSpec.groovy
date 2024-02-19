package com.raynigon.unit.api.core.math

import spock.lang.Specification

import static com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.Metre

class QuantityMathSpec extends Specification {

    def "minimum - #a, #b"() {
        expect:
        QuantityMath.min(a, b) == c
        where:
        a        | b         | c
        Metre(0) | Metre(1)  | Metre(0)
        Metre(2) | Metre(1)  | Metre(1)
        Metre(0) | Metre(0)  | Metre(0)
        Metre(0) | Metre(-1) | Metre(-1)
    }

    def "maximum - #a, #b"() {
        expect:
        QuantityMath.max(a, b) == c
        where:
        a         | b         | c
        Metre(0)  | Metre(1)  | Metre(1)
        Metre(2)  | Metre(1)  | Metre(2)
        Metre(0)  | Metre(0)  | Metre(0)
        Metre(-2) | Metre(-1) | Metre(-1)
    }

    def "minimum - #a, #b, #c"() {
        expect:
        QuantityMath.min(a, b, c) == z
        where:
        a         | b         | c         | z
        Metre(0)  | Metre(1)  | Metre(2)  | Metre(0)
        Metre(2)  | Metre(1)  | Metre(1)  | Metre(1)
        Metre(0)  | Metre(0)  | Metre(0)  | Metre(0)
        Metre(-3) | Metre(-2) | Metre(-1) | Metre(-3)
    }

    def "maximum - #a, #b, #c"() {
        expect:
        QuantityMath.max(a, b, c) == z
        where:
        a         | b         | c         | z
        Metre(0)  | Metre(1)  | Metre(2)  | Metre(2)
        Metre(3)  | Metre(2)  | Metre(1)  | Metre(3)
        Metre(0)  | Metre(0)  | Metre(0)  | Metre(0)
        Metre(-3) | Metre(-2) | Metre(-1) | Metre(-1)
    }

    def "minimum with null"() {
        when:
        QuantityMath.min(a, b)

        then:
        thrown(NullPointerException)

        where:
        a        | b
        null     | null
        Metre(0) | null
        null     | Metre(0)
    }

    def "maximum with null"() {
        when:
        QuantityMath.min(a, b)

        then:
        thrown(NullPointerException)

        where:
        a        | b
        null     | null
        Metre(0) | null
        null     | Metre(0)
    }

    def "clamp - #min, #value, #max"() {
        when:
        QuantityMath.clamp(min, value, max)

        then:
        thrown(NullPointerException)

        where:
        min      | value    | max
        null     | null     | null
        Metre(0) | null     | null
        null     | Metre(0) | null
        null     | null     | Metre(0)
        Metre(0) | Metre(0) | null
        null     | Metre(0) | Metre(0)
    }

    def "clamp handles min greater than max"() {
        given:
        def min = Metre(10)
        def value = Metre(5)
        def max = Metre(9)

        when:
        QuantityMath.clamp(min, value, max)

        then:
        thrown(IllegalArgumentException)
    }

    def "clamp with null"() {
        when:
        QuantityMath.clamp(min, value, max)

        then:
        thrown(NullPointerException)

        where:
        min      | value    | max
        null     | null     | null
        Metre(0) | null     | null
        null     | Metre(0) | null
        null     | null     | Metre(0)
        Metre(0) | Metre(0) | null
        null     | Metre(0) | Metre(0)
    }
}
