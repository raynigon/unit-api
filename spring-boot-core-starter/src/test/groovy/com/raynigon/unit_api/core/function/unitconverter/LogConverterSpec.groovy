package com.raynigon.unit_api.core.function.unitconverter


import spock.lang.Specification
import spock.lang.Unroll

import static ConverterTestUtils.closeTo

class LogConverterSpec extends Specification {

    def 'identity returns false'() {
        expect:
        !new LogConverter(10.0).isIdentity()
    }

    def 'get base returns base value'() {
        given:
        def converter = new LogConverter(10.0)

        when:
        def base = converter.getBase()

        then:
        closeTo(base, 10.0, 0.001)
    }

    def 'two converters with the same base are equal'() {
        expect:
        new LogConverter(10.0) != null
        new LogConverter(10.0) == new LogConverter(10.0)
    }

    def 'toString method formats correctly for base #base'() {
        expect:
        new LogConverter(base).toString() == expected

        where:
        base   | expected
        10.0d  | "Log(x -> log(base=10.0, x))"
        Math.E | "Log(x -> ln(x))"
    }

    def 'converter is not linear'() {
        expect:
        !new LogConverter(10.0d).isLinear()
    }

    def 'conversion with exception'() {
        given:
        LogConverter converter = new LogConverter(10)

        when:
        converter.convert(value)

        then:
        thrown(NumberFormatException)

        where:
        value << [-10, -1, 0]
    }

    @Unroll
    def 'convert #value to #expected'() {
        given:
        LogConverter converter = new LogConverter(10)

        when:
        def result = converter.convert(value)

        then:
        closeTo(result, expected, 0.0001)

        where:
        value | expected
        10    | 1
        100   | 2
        1000  | 3
    }

}
