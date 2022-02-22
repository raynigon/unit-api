package com.raynigon.unit.api.core.function.numbersystem.types

import com.raynigon.unit.api.core.function.RationalNumber
import spock.lang.Specification
import spock.lang.Subject

class IntegerHelperSpec extends Specification {

    @Subject
    IntegerHelper helper = new IntegerHelper()

    def "reciprocal of #input is #dividend/#divisor"() {
        given:
        RationalNumber outputRN = RationalNumber.of(dividend, divisor)

        when:
        def result = helper.reciprocal(input)

        then:
        result == outputRN

        where:
        input | dividend | divisor
        1     | 1        | 1
        2     | 1        | 2
        -1    | -1       | 1
        -2    | -1       | 2
    }

    def "negate of #input returns #output"() {
        when:
        def result = helper.negate(input)

        then:
        result == output

        where:
        input | output
        1     | -1
        2     | -2
        -1    | 1
        -2    | 2
    }

    def "signum of #input is #output"() {
        when:
        def result = helper.signum(input)

        then:
        result == output

        where:
        input | output
        2     | 1
        1     | 1
        0     | 0
        -1    | -1
        -2    | -1
    }

    def "abs of #input is #output"() {
        when:
        def result = helper.abs(input)

        then:
        result == output

        where:
        input | output
        1     | 1
        2     | 2
        0     | 0
        -1    | 1
        -2    | 2
    }

    def "e^#input is #output"() {
        given:

        when:
        def result = helper.exp(input)

        then:
        Math.abs((result - output).doubleValue()) <= 0.00001

        where:
        input | output
        1     | Math.E
        0     | 1.0d
    }

    def "ln(#input) = #output"() {
        when:
        def result = helper.log(input)

        then:
        Math.abs(result.doubleValue() - output) <= 0.00001

        where:
        input | output
        1     | 0.0d
        3     | 1.09861d
    }

    def "Number #input is zero #output"() {
        when:
        def result = helper.isZero(input)

        then:
        result == output

        where:
        input | output
        1     | false
        -1    | false
        0     | true
    }

    def "Number #input is one #output"() {
        when:
        def result = helper.isOne(input)

        then:
        result == output

        where:
        input | output
        1     | true
        -1    | false
        0     | false
    }

    def "Number #input is less than one #output"() {
        when:
        def result = helper.isLessThanOne(input)

        then:
        result == output

        where:
        input | output
        1     | false
        -1    | true
        0     | true
    }

    def "Number #input is Integer #output"() {
        when:
        def result = helper.isInteger(input)

        then:
        result == output

        where:
        input | output
        1     | true
        -1    | true
        0     | true
    }

    def "narrow #input to #output"() {
        expect:
        helper.narrow(input) == output

        where:
        input | output
        0     | 0
        1     | 1
        -1    | -1
    }

    def "calculate #input^#exp = #output"() {
        expect:
        helper.power(input, exp) == output

        where:
        input | exp | output
        1     | 1   | 1
        2     | 2   | 4
        -3    | 2   | 9
        -3    | 3   | -27
    }

    def "convert BigInteger to BigInteger"() {
        expect:
        helper.toBigDecimal(1) == BigDecimal.valueOf(1.0)
    }
}
