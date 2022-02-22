package com.raynigon.unit.api.core.function.numbersystem.types

import com.raynigon.unit.api.core.function.RationalNumber
import spock.lang.Specification
import spock.lang.Subject

class BigDecimalHelperSpec extends Specification {

    @Subject
    BigDecimalHelper helper = new BigDecimalHelper()

    def "reciprocal of #input is #dividend/#divisor"() {
        given:
        BigDecimal inputBD = new BigDecimal(input)
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
        BigDecimal inputBD = new BigDecimal(input)
        BigDecimal outputBD = new BigDecimal(output)

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
        BigDecimal inputBD = new BigDecimal(input)

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
        BigDecimal inputBD = new BigDecimal(input)
        BigDecimal outputBD = new BigDecimal(output)

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
        BigDecimal inputBD = new BigDecimal(input)
        BigDecimal outputBD = new BigDecimal(output)

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
        BigDecimal inputBD = new BigDecimal(input)
        BigDecimal outputBD = new BigDecimal(output)

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
        def result = helper.isZero(BigDecimal.valueOf(input))

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
        def result = helper.isOne(BigDecimal.valueOf(input))

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
        def result = helper.isLessThanOne(BigDecimal.valueOf(input))

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
        def result = helper.isInteger(BigDecimal.valueOf(input))

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

    def "convert BigDecimal to BigDecimal"() {
        expect:
        helper.toBigDecimal(BigDecimal.valueOf(1.2)) == BigDecimal.valueOf(1.2)
    }
}
