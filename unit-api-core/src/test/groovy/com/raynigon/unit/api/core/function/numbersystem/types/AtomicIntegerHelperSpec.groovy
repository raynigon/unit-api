package com.raynigon.unit.api.core.function.numbersystem.types

import com.raynigon.unit.api.core.function.RationalNumber
import spock.lang.Specification
import spock.lang.Subject

import java.util.concurrent.atomic.AtomicInteger

class AtomicIntegerHelperSpec extends Specification {

    @Subject
    AtomicIntegerHelper helper = new AtomicIntegerHelper()

    def "reciprocal of #input is #dividend/#divisor"() {
        given:
        AtomicInteger inputAI = new AtomicInteger(input)
        RationalNumber outputRN = RationalNumber.of(dividend, divisor)

        when:
        def result = helper.reciprocal(inputAI)

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
        given:
        AtomicInteger inputAI = new AtomicInteger(input)

        when:
        def result = helper.negate(inputAI)

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
        given:
        AtomicInteger inputAI = new AtomicInteger(input)

        when:
        def result = helper.signum(inputAI)

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
        AtomicInteger inputAI = new AtomicInteger(input)

        when:
        def result = helper.abs(inputAI)

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
        AtomicInteger inputAI = new AtomicInteger(input)

        when:
        def result = helper.exp(inputAI)

        then:
        Math.abs((result - output).doubleValue()) <= 0.00001

        where:
        input | output
        1     | Math.E
        0     | 1.0d
    }

    def "ln(#input) = #output"() {
        given:
        AtomicInteger inputAI = new AtomicInteger(input)

        when:
        def result = helper.log(inputAI)

        then:
        Math.abs(result.doubleValue() - output) <= 0.00001

        where:
        input | output
        1     | 0.0d
        3     | 1.09861d
    }

    def "Number #input is zero #output"() {
        given:
        AtomicInteger inputAI = new AtomicInteger(input)

        when:
        def result = helper.isZero(inputAI)

        then:
        result == output

        where:
        input | output
        1     | false
        -1    | false
        0     | true
    }

    def "Number #input is one #output"() {
        given:
        AtomicInteger inputAI = new AtomicInteger(input)

        when:
        def result = helper.isOne(inputAI)

        then:
        result == output

        where:
        input | output
        1     | true
        -1    | false
        0     | false
    }

    def "Number #input is less than one #output"() {
        given:
        AtomicInteger inputAI = new AtomicInteger(input)

        when:
        def result = helper.isLessThanOne(inputAI)

        then:
        result == output

        where:
        input | output
        1     | false
        -1    | true
        0     | true
    }

    def "Number #input is Integer #output"() {
        given:
        AtomicInteger inputAI = new AtomicInteger(input)

        when:
        def result = helper.isInteger(inputAI)

        then:
        result == output

        where:
        input | output
        1     | true
        -1    | true
        0     | true
    }

    def "narrow #input to #output"() {
        given:
        AtomicInteger inputAI = new AtomicInteger(input)

        expect:
        ((AtomicInteger) helper.narrow(inputAI)).get() == output

        where:
        input | output
        0     | 0
        1     | 1
        -1    | -1
    }

    def "calculate #input^#exp = #output"() {
        given:
        AtomicInteger inputAI = new AtomicInteger(input)

        expect:
        helper.power(inputAI, exp) == output

        where:
        input | exp | output
        1     | 1   | 1
        2     | 2   | 4
        -3    | 2   | 9
        -3    | 3   | -27
    }

    def "convert BigInteger to BigInteger"() {
        expect:
        helper.toBigDecimal(new AtomicInteger(1)) == BigDecimal.valueOf(1.0)
    }
}
