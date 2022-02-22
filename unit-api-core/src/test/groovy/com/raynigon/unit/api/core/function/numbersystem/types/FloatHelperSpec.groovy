package com.raynigon.unit.api.core.function.numbersystem.types

import com.raynigon.unit.api.core.function.RationalNumber
import spock.lang.Specification
import spock.lang.Subject

class FloatHelperSpec extends Specification {

    @Subject
    FloatHelper helper = new FloatHelper()

    def "reciprocal of #input is #dividend/#divisor"() {
        given:
        RationalNumber outputRN = RationalNumber.of(dividend, divisor)

        when:
        def result = helper.reciprocal(input)

        then:
        result == outputRN

        where:
        input | dividend | divisor
        1.0f  | 1L       | 1L
        0.5f  | 2L       | 1L
        2.0f  | 1L       | 2L
    }

    def "negate of #input returns #output"() {
        when:
        def result = helper.negate(input)

        then:
        result == output

        where:
        input | output
        1.0f  | -1.0f
        0.5f  | -0.5f
        2.0f  | -2.0f
        -1.0f | 1.0f
        -0.5f | 0.5f
        -2.0f | 2.0f
    }

    def "signum of #input is #output"() {
        when:
        def result = helper.signum(input)

        then:
        result == output

        where:
        input | output
        2.0f  | 1
        1.5f  | 1
        1.0f  | 1
        0.5f  | 1
        0.0f  | 0
        -0.5f | -1
        -1.0f | -1
        -1.5f | -1
        -2.0f | -1
    }

    def "abs of #input is #output"() {
        when:
        def result = helper.abs(input)

        then:
        result == output

        where:
        input | output
        1.0f  | 1.0f
        0.5f  | 0.5f
        2.0f  | 2.0f
        0.0f  | 0.0f
        -1.0f | 1.0f
        -0.5f | 0.5f
        -2.0f | 2.0f
    }

    def "e^#input is #output"() {
        when:
        def result = helper.exp(input)

        then:
        Math.abs((result - output).doubleValue()) <= 0.00001

        where:
        input | output
        1.0f  | Math.E
        0.0f  | 1.0f
    }

    def "ln(#input) = #output"() {
        when:
        def result = helper.log(input)

        then:
        Math.abs((result - output).doubleValue()) <= 0.00001

        where:
        input                          | output
        Float.valueOf(Math.E as float) | 1.0d
        1.0f                           | 0.0d
    }

    def "Number #input is zero #output"() {
        when:
        def result = helper.isZero(input)

        then:
        result == output

        where:
        input | output
        1.0f  | false
        -1.0f | false
        0.1f  | false
        -0.1f | false
        0.0f  | true
    }

    def "Number #input is one #output"() {
        when:
        def result = helper.isOne(input)

        then:
        result == output

        where:
        input | output
        1.0f  | true
        -1.0f | false
        0.1f  | false
        -0.1f | false
        0.0f  | false
    }

    def "Number #input is less than one #output"() {
        when:
        def result = helper.isLessThanOne(input)

        then:
        result == output

        where:
        input | output
        1.0f  | false
        -1.0f | true
        0.1f  | true
        -0.1f | true
        0.0f  | true
    }

    def "Number #input is Integer #output"() {
        when:
        def result = helper.isInteger(input)

        then:
        result == output

        where:
        input | output
        1.0f  | true
        -1.0f | true
        0.1f  | false
        -0.1f | false
        0.0f  | true
    }

    def "narrow #input to #output"() {
        expect:
        helper.narrow(input) == output

        where:
        input | output
        0.0f  | 0.0f
        1.0f  | 1.0f
    }

    def "calculate #input^#exp = #output"() {
        expect:
        helper.power(input, exp) == output

        where:
        input | exp | output
        1.0f  | 1   | 1.0f
        2.0f  | 2   | 4.0f
        2.5f  | 2   | 6.25f

    }

    def "convert BigDecimal to BigDecimal"() {
        expect:
        Math.abs((helper.toBigDecimal(1.3f) - BigDecimal.valueOf(1.3)).doubleValue()) <= 0.00001
    }
}
