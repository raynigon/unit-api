package com.raynigon.unit.api.core.function.numbersystem.types

import com.raynigon.unit.api.core.function.RationalNumber
import spock.lang.Specification
import spock.lang.Subject

class LongHelperSpec extends Specification {

    @Subject
    LongHelper helper = new LongHelper()

    def "reciprocal of #input is #dividend/#divisor"() {
        given:
        RationalNumber outputRN = RationalNumber.of(dividend, divisor)

        when:
        def result = helper.reciprocal(input)

        then:
        result == outputRN

        where:
        input | dividend | divisor
        1     | 1L       | 1L
        2     | 1L       | 2L
        -1    | -1L      | 1L
        -2    | -1L      | 2L
    }

    def "negate of #input returns #output"() {
        when:
        def result = helper.negate(input)

        then:
        result == output

        where:
        input | output
        1L    | -1L
        2L    | -2L
        -1L   | 1L
        -2L   | 2L
    }

    def "signum of #input is #output"() {
        when:
        def result = helper.signum(input)

        then:
        result == output

        where:
        input | output
        2L    | 1L
        1L    | 1L
        0L    | 0L
        -1L   | -1L
        -2L   | -1L
    }

    def "abs of #input is #output"() {
        when:
        def result = helper.abs(input)

        then:
        result == output

        where:
        input | output
        1L    | 1L
        2L    | 2L
        0L    | 0L
        -1L   | 1L
        -2L   | 2L
    }

    def "e^#input is #output"() {
        given:

        when:
        def result = helper.exp(input)

        then:
        Math.abs((result - output).doubleValue()) <= 0.00001

        where:
        input | output
        1L    | Math.E
        0L    | 1.0d
    }

    def "ln(#input) = #output"() {
        when:
        def result = helper.log(input)

        then:
        Math.abs(result.doubleValue() - output) <= 0.00001

        where:
        input | output
        1L    | 0.0d
        3L    | 1.0d
    }

    def "Number #input is zero #output"() {
        when:
        def result = helper.isZero(input)

        then:
        result == output

        where:
        input | output
        1L    | false
        -1L   | false
        0L    | true
    }

    def "Number #input is one #output"() {
        when:
        def result = helper.isOne(input)

        then:
        result == output

        where:
        input | output
        1L    | true
        -1L   | false
        0L    | false
    }

    def "Number #input is less than one #output"() {
        when:
        def result = helper.isLessThanOne(input)

        then:
        result == output

        where:
        input | output
        1L    | false
        -1L   | true
        0L    | true
    }

    def "Number #input is Integer #output"() {
        when:
        def result = helper.isInteger(input)

        then:
        result == output

        where:
        input | output
        1L    | true
        -1L   | true
        0L    | true
    }

    def "narrow #input to #output"() {
        expect:
        helper.narrow(input) == output

        where:
        input | output
        0L    | 0L
        1L    | 1L
        -1L   | -1L
    }

    def "calculate #input^#exp = #output"() {
        expect:
        helper.power(input, exp) == output

        where:
        input | exp | output
        1L    | 1   | 1L
        2L    | 2   | 4L
        -3L   | 2   | 9L
        -3L   | 3   | -27L
    }

    def "convert BigInteger to BigInteger"() {
        expect:
        helper.toBigDecimal(1L) == BigDecimal.valueOf(1.0)
    }
}
