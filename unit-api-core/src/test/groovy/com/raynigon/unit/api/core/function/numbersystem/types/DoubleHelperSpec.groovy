package com.raynigon.unit.api.core.function.numbersystem.types

import com.raynigon.unit.api.core.function.RationalNumber
import spock.lang.Specification
import spock.lang.Subject

class DoubleHelperSpec extends Specification {

    @Subject
    DoubleHelper helper = new DoubleHelper()

    def "reciprocal of #input is #dividend/#divisor"() {
        given:
        RationalNumber outputRN = RationalNumber.of(dividend, divisor)

        when:
        def result = helper.reciprocal(input)

        then:
        result == outputRN

        where:
        input | dividend | divisor
        1.0d  | 1L       | 1L
        0.5d  | 2L       | 1L
        2.0d  | 1L       | 2L
    }

    def "negate of #input returns #output"() {
        when:
        def result = helper.negate(input)

        then:
        result == output

        where:
        input | output
        1.0d  | -1.0
        0.5d  | -0.5
        2.0d  | -2.0
        -1.0d | 1.0
        -0.5d | 0.5
        -2.0d | 2.0
    }

    def "signum of #input is #output"() {
        when:
        def result = helper.signum(input)

        then:
        result == output

        where:
        input | output
        2.0d  | 1
        1.5d  | 1
        1.0d  | 1
        0.5d  | 1
        0.0d  | 0
        -0.5d | -1
        -1.0d | -1
        -1.5d | -1
        -2.0d | -1
    }

    def "abs of #input is #output"() {
        when:
        def result = helper.abs(input)

        then:
        result == output

        where:
        input | output
        1.0d  | 1.0
        0.5d  | 0.5
        2.0d  | 2.0
        0.0d  | 0.0
        -1.0d | 1.0
        -0.5d | 0.5
        -2.0d | 2.0
    }

    def "e^#input is #output"() {
        when:
        def result = helper.exp(input)

        then:
        Math.abs((result - output).doubleValue()) <= 0.00001

        where:
        input | output
        1.0d  | Math.E
        0.0d  | 1.0d
    }

    def "ln(#input) = #output"() {
        when:
        def result = helper.log(input)

        then:
        Math.abs((result - output).doubleValue()) <= 0.00001

        where:
        input  | output
        Math.E | 1.0
        1.0d   | 0.0
    }

    def "Number #input is zero #output"() {
        when:
        def result = helper.isZero(input)

        then:
        result == output

        where:
        input | output
        1.0d  | false
        -1.0d | false
        0.1d  | false
        -0.1d | false
        0.0d  | true
    }

    def "Number #input is one #output"() {
        when:
        def result = helper.isOne(input)

        then:
        result == output

        where:
        input | output
        1.0d  | true
        -1.0d | false
        0.1d  | false
        -0.1d | false
        0.0d  | false
    }

    def "Number #input is less than one #output"() {
        when:
        def result = helper.isLessThanOne(input)

        then:
        result == output

        where:
        input | output
        1.0d  | false
        -1.0d | true
        0.1d  | true
        -0.1d | true
        0.0d  | true
    }

    def "Number #input is Integer #output"() {
        when:
        def result = helper.isInteger(input)

        then:
        result == output

        where:
        input | output
        1.0d  | true
        -1.0d | true
        0.1d  | false
        -0.1d | false
        0.0d  | true
    }

    def "narrow #input to #output"() {
        expect:
        helper.narrow(input) == output

        where:
        input | output
        0.0d  | 0.0d
        1.0d  | 1.0d
    }

    def "calculate #input^#exp = #output"() {
        expect:
        helper.power(input, exp) == output

        where:
        input | exp | output
        1.0d  | 1   | 1.0d
        2.0d  | 2   | 4.0d
        2.5d  | 2   | 6.25d

    }

    def "convert BigDecimal to BigDecimal"() {
        expect:
        Math.abs((helper.toBigDecimal(1.3d) - BigDecimal.valueOf(1.3)).doubleValue()) <= 0.00001
    }
}
