package com.raynigon.unit_api.core.function

import spock.lang.Ignore
import spock.lang.Specification

import static com.raynigon.unit_api.core.function.ConverterTestUtils.closeTo

class PowerOfPiConverterSpec extends Specification {

    def 'convert with double'() {
        given:
        PowerOfPiConverter converter = new PowerOfPiConverter(-1);

        when:
        def result = converter.convert(value)

        then:
        closeTo(result, expectedResult, 0.2)

        where:
        value | expectedResult
        3141  | 1000
        0     | 0
        -3141 | -1000
    }

    def 'convert with BigDecimal'() {
        given:
        PowerOfPiConverter converter = new PowerOfPiConverter(-1);

        when:
        def result = converter.convert(value)

        then:
        closeTo(result, expectedResult, 0.2)

        where:
        value                   | expectedResult
        new BigDecimal("3141")  | 1000
        BigDecimal.ZERO         | 0
        new BigDecimal("-3141") | -1000
    }

    def 'equal converters should be equals'() {
        given:
        PowerOfPiConverter a = new PowerOfPiConverter(-1);
        PowerOfPiConverter b = new PowerOfPiConverter(-1);
        PowerOfPiConverter c = PowerOfPiConverter.ONE;

        when:
        true

        then:
        a == b
        a != c
    }

    def isLinear() {
        expect:
        PowerOfPiConverter.of(-1).isLinear()
    }

    def piSquaredBigDecimalDefaultPrecision() {
        given:
        PowerOfPiConverter converter = new PowerOfPiConverter(2);

        when:
        BigDecimal value = (BigDecimal) converter.convert(BigDecimal.valueOf(0.1));

        then:
        value.toPlainString() == "0.9869604401089358618834490999876151"
    }

    @Ignore
    def piBigDecimalDefaultPrecision() {
        given:
        PowerOfPiConverter converter = new PowerOfPiConverter(1);
        /*
         * For this test the following Context has to be used
         *  Calculus.MATH_CONTEXT = MathContext.UNLIMITED;
         * */
        when:
        converter.convert(BigDecimal.valueOf(1.0))

        then:
        thrown(ArithmeticException)
    }

    @Ignore
    def piBigDecimalExtendedPrecision() {
        given:
        PowerOfPiConverter converter = PowerOfPiConverter.ONE;
        /*
         * For this test the following Context has to be used
         *  Calculus.MATH_CONTEXT = new MathContext(MathContext.DECIMAL128.getPrecision() * 2);
         * */
        when:
        BigDecimal value = (BigDecimal) converter.convert(BigDecimal.valueOf(1.0));

        then:
        value.toPlainString() == "3.14159265358979323846264338327950288419716939937510582097494459230781"
    }

    def 'toString method formats correctly for base #exponent'() {
        expect:
        PowerOfPiConverter converter = new PowerOfPiConverter(exponent);
        converter.toString() == expected

        where:
        exponent | expected
        2        | "PowerOfPi(x -> x * π^2)"
        3        | "PowerOfPi(x -> x * π^3)"
    }
}
