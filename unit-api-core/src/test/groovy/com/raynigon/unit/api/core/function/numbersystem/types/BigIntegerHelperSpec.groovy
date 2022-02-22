package com.raynigon.unit.api.core.function.numbersystem.types

import com.raynigon.unit.api.core.function.RationalNumber
import spock.lang.Specification
import spock.lang.Subject

class BigIntegerHelperSpec extends Specification {

    @Subject
    BigIntegerHelper helper = new BigIntegerHelper()

    def "reciprocal of #input is #dividend/#divisor"() {
        given:
        BigInteger inputBD = BigInteger.valueOf(input)
        RationalNumber outputRN = RationalNumber.of(dividend, divisor)

        when:
        def result = helper.reciprocal(inputBD)

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
        given:
        BigInteger inputBD = BigInteger.valueOf(input)
        BigInteger outputBD = BigInteger.valueOf(output)

        when:
        def result = helper.negate(inputBD)

        then:
        result == outputBD

        where:
        input | output
        1     | -1
        2     | -2
        -1    | 1
        -2    | 2
    }

    def "signum of #input is #output"() {
        given:
        BigInteger inputBD = BigInteger.valueOf(input)

        when:
        def result = helper.signum(inputBD)

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
        given:
        BigInteger inputBD = BigInteger.valueOf(input)
        BigInteger outputBD = BigInteger.valueOf(output)

        when:
        def result = helper.abs(inputBD)

        then:
        result == outputBD

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
        BigInteger inputBD = BigInteger.valueOf(input)
        BigDecimal outputBD = BigDecimal.valueOf(output)

        when:
        def result = helper.exp(inputBD)

        then:
        Math.abs((result - outputBD).doubleValue()) <= 0.00001

        where:
        input | output
        1     | Math.E
        0     | 1.0d
    }

    def "ln(#input) = #output"() {
        given:
        BigInteger inputBD = BigInteger.valueOf(input)
        BigDecimal outputBD = BigDecimal.valueOf(output)

        when:
        def result = helper.log(inputBD)

        then:
        Math.abs((result - outputBD).doubleValue()) <= 0.00001

        where:
        input | output
        1     | 0.0
        3     | 1.0
    }

    def "Number #input is zero #output"() {
        when:
        def result = helper.isZero(BigInteger.valueOf(input))

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
        def result = helper.isOne(BigInteger.valueOf(input))

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
        def result = helper.isLessThanOne(BigInteger.valueOf(input))

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
        def result = helper.isInteger(BigInteger.valueOf(input))

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
        helper.narrow(new BigInteger(input)) == output

        where:
        input                                                                          | output
        "0"                                                                            | 0
        "1"                                                                            | 1
        "3141592653589793238462643383279502884197169399375105820974944592307816406286" | new BigInteger("3141592653589793238462643383279502884197169399375105820974944592307816406286")
    }

    def "calculate #input^#exp = #output"() {
        expect:
        helper.power(BigInteger.valueOf(input), exp) == BigInteger.valueOf(output)

        where:
        input | exp | output
        1     | 1   | 1
        2     | 2   | 4
        -3    | 2   | 9
        -4    | 3   | -64

    }

    def "convert BigInteger to BigInteger"() {
        expect:
        helper.toBigDecimal(BigInteger.valueOf(1)) == BigDecimal.valueOf(1.0)
    }
}
