package com.raynigon.unit_api.core.function

import spock.lang.Specification

import static com.raynigon.unit_api.core.function.ConverterTestUtils.closeTo

class ExpConverterSpec extends Specification {

    def 'identity returns false'(){
        expect:
        !new ExpConverter(10.0).isIdentity()
    }

    def 'get base returns base value'() {
        given:
        def converter = new ExpConverter(10.0)

        when:
        def base = converter.getBase()

        then:
        closeTo(base, 10.0, 0.001)
    }

    def 'two converters with the same base are equal'() {
        expect:
        new ExpConverter(10.0) != null
        new ExpConverter(10.0) == new ExpConverter(10.0)
    }

    def 'toString method formats correctly for base #base'() {
        expect:
        new ExpConverter(base).toString() == expected

        where:
        base   | expected
        10.0d  | "Exp(x -> 10.0^x)"
        Math.E | "Exp(x -> e^x)"
    }

    def 'converter is not linear'() {
        expect:
        !new ExpConverter(10.0d).isLinear()
    }

    def 'inverse of ExpConverter is LogConverter'() {
        given:
        ExpConverter expConverter = ExpConverter.of(base as double)
        AbstractConverter invertedConverter = expConverter.inverse()
        LogConverter logConverter = new LogConverter(base as double)

        when:
        def result = logConverter.convert(expConverter.convert(value))

        then:
        invertedConverter == logConverter

        and:
        result == value

        where:
        base   | value
        Math.E | 1.0
        Math.E | 0.0
        Math.E | -1.0
        10.0d  | 1.0
        10.0d  | 0.0
        10.0d  | -1.0
    }
}
