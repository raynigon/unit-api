package com.raynigon.unit.api.core.function.unitconverter


import spock.lang.Specification
import spock.lang.Unroll

import static ConverterTestUtils.closeTo

class DoubleMultiplyConverterSpec extends Specification {

    def 'conversion with factor 2'() {
        given:
        def converter = DoubleMultiplyConverter.of(factor);

        when:
        def actual = converter.convert(value)

        then:
        closeTo(actual, expected, 0.001);

        where:
        value | factor | expected
        100   | 2      | 200
        0     | 2      | 0
        -100  | 2      | -200
    }

    def 'two converters with the same factor are equal'() {
        expect:
        DoubleMultiplyConverter.of(2) != null
        DoubleMultiplyConverter.of(2) == DoubleMultiplyConverter.of(2)
    }

    def 'value getter returns factor'() {
        expect:
        DoubleMultiplyConverter.of(2).getValue() == Double.valueOf(2d)
    }

    def 'converter is linear'() {
        expect:
        DoubleMultiplyConverter.of(2).isLinear()
    }

    def 'inverse calculation'() {
        given:
        def originalConverter = DoubleMultiplyConverter.of(2d)
        def expectedConverter = DoubleMultiplyConverter.of(0.5d)

        when:
        def inverse = originalConverter.inverse()

        then:
        inverse != null
        inverse.equals(expectedConverter)
    }

    @Unroll
    def 'identity calculation returns #expectedIdentity for factor #factor'() {
        expect:
        DoubleMultiplyConverter.of(factor).isIdentity() == expectedIdentity

        where:
        factor | expectedIdentity
        -1     | false
        0      | false
        1      | true
        2      | false
    }

    def 'toString method formats correctly'() {
        expect:
        DoubleMultiplyConverter.of(2).toString() == "DoubleMultiply(x -> x * 2.0)"
    }
}
