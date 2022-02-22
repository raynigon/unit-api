package com.raynigon.unit.api.core.function.numbersystem.types

import com.raynigon.unit.api.core.function.RationalNumber
import spock.lang.Specification
import spock.lang.Subject

class RationalNumberHelperSpec extends Specification {

    @Subject
    RationalNumberHelper helper = new RationalNumberHelper()

    def "reciprocal of #input is #dividend/#divisor"() {
        given:
        RationalNumber inputBD = RationalNumber.of(input)
        RationalNumber outputRN = RationalNumber.of(dividend, divisor)

        when:
        def result = helper.reciprocal(inputBD)

        then:
        result == outputRN

        where:
        input | dividend | divisor
        1.0   | 1L       | 1L
        0.5   | 2L       | 1L
        2.0   | 1L       | 2L
    }

    def "negate of #input returns #output"() {
        given:
        RationalNumber inputBD = RationalNumber.of(input)
        RationalNumber outputBD = RationalNumber.of(output)

        when:
        def result = helper.negate(inputBD)

        then:
        result == outputBD

        where:
        input | output
        1.0   | -1.0
        0.5   | -0.5
        2.0   | -2.0
        -1.0  | 1.0
        -0.5  | 0.5
        -2.0  | 2.0
    }

    def "signum of #input is #output"() {
        given:
        RationalNumber inputBD = RationalNumber.of(input)

        when:
        def result = helper.signum(inputBD)

        then:
        result == output

        where:
        input | output
        2.0   | 1
        1.5   | 1
        1.0   | 1
        0.5   | 1
        0.0   | 0
        -0.5  | -1
        -1.0  | -1
        -1.5  | -1
        -2.0  | -1
    }

    def "abs of #input is #output"() {
        given:
        RationalNumber inputBD = RationalNumber.of(input)
        RationalNumber outputBD = RationalNumber.of(output)

        when:
        def result = helper.abs(inputBD)

        then:
        result == outputBD

        where:
        input | output
        1.0   | 1.0
        0.5   | 0.5
        2.0   | 2.0
        0.0   | 0.0
        -1.0  | 1.0
        -0.5  | 0.5
        -2.0  | 2.0
    }

    def "e^#input is #output"() {
        given:
        RationalNumber inputBD = RationalNumber.of(input)
        RationalNumber outputBD = RationalNumber.of(output)

        when:
        def result = helper.exp(inputBD)

        then:
        Math.abs((result - outputBD).doubleValue()) <= 0.00001

        where:
        input | output
        1.0   | Math.E
        0.0   | 1.0d
    }

    def "ln(#input) = #output"() {
        given:
        RationalNumber inputBD = RationalNumber.of(input)
        RationalNumber outputBD = RationalNumber.of(output)

        when:
        def result = helper.log(inputBD)

        then:
        Math.abs((result - outputBD).doubleValue()) <= 0.00001

        where:
        input  | output
        Math.E | 1.0
        1.0d   | 0.0
    }

    def "Number #input is zero #output"() {
        when:
        def result = helper.isZero(RationalNumber.of(input))

        then:
        result == output

        where:
        input | output
        1.0   | false
        -1.0  | false
        0.1   | false
        -0.1  | false
        0.0   | true
    }

    def "Number #input is one #output"() {
        when:
        def result = helper.isOne(RationalNumber.of(input))

        then:
        result == output

        where:
        input | output
        1.0   | true
        -1.0  | false
        0.1   | false
        -0.1  | false
        0.0   | false
    }

    def "Number #input is less than one #output"() {
        when:
        def result = helper.isLessThanOne(RationalNumber.of(input))

        then:
        result == output

        where:
        input | output
        1.0   | false
        -1.0  | true
        0.1   | true
        -0.1  | true
        0.0   | true
    }

    def "Number #input is Integer #output"() {
        when:
        def result = helper.isInteger(RationalNumber.of(input))

        then:
        result == output

        where:
        input | output
        1.0   | true
        -1.0  | true
        0.1   | false
        -0.1  | false
        0.0   | true
    }

    def "narrow #input to #output"() {
        expect:
        helper.narrow(RationalNumber.of(input)) == output

        where:
        input | output
        0.0   | 0.0
        1.0   | 1.0
    }

    def "calculate #input^#exp = #output"() {
        expect:
        helper.power(RationalNumber.of(input), exp) == RationalNumber.of(output)

        where:
        input | exp | output
        1.0   | 1   | 1.0
        2.0   | 2   | 4.0
        2.5   | 2   | 6.25

    }

    def "convert BigDecimal to BigDecimal"() {
        expect:
        helper.toBigDecimal(RationalNumber.of(1.2)) == BigDecimal.valueOf(1.2)
    }
}
