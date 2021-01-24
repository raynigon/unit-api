package com.raynigon.unit_api.core.function.numbersystem

import com.raynigon.unit_api.core.function.NumberSystem
import com.raynigon.unit_api.core.function.RationalNumber
import spock.lang.Specification
import spock.lang.Unroll

import static com.raynigon.unit_api.core.function.unitconverter.ConverterTestUtils.closeTo

class DefaultNumberSystemSpec extends Specification {


    @Unroll
    def "add numbers - result of type #expectedType"() {
        given:
        def numberSystem = new DefaultNumberSystem()

        when:
        Number result = numberSystem.add(x, y)

        then:
        expectedType.isInstance(result)

        and:
        result == expectedResult

        where:
        x    | y    | expectedResult | expectedType
        1.0f | 2.0d | 3.0            | BigDecimal.class
        2.0d | 3.0d | 5.0            | BigDecimal.class
    }

    @Unroll
    def "subtract numbers - result of type #expectedType"() {
        given:
        def numberSystem = new DefaultNumberSystem()

        when:
        Number result = numberSystem.subtract(x, y)

        then:
        expectedType.isInstance(result)

        and:
        result == expectedResult

        where:
        x    | y    | expectedResult | expectedType
        2.0f | 1.0d | 1.0            | BigDecimal.class
        5.0d | 3.0d | 2.0            | BigDecimal.class
    }

    @Unroll
    def "multiply numbers - result of type #expectedType"() {
        given:
        def numberSystem = new DefaultNumberSystem()

        when:
        Number result = numberSystem.multiply(x, y)

        then:
        expectedType.isInstance(result)

        and:
        result == expectedResult

        where:
        x    | y    | expectedResult | expectedType
        2.0f | 1.0d | 2.0            | Double.class
        5.0d | 3.0d | 15.0           | Double.class
    }

    @Unroll
    def "divide numbers - result of type #expectedType"() {
        given:
        def numberSystem = new DefaultNumberSystem()

        when:
        Number result = numberSystem.divide(x, y)

        then:
        expectedType.isInstance(result)

        and:
        closeTo(result, expectedResult, 0.0001)

        where:
        x    | y    | expectedResult | expectedType
        4.0f | 2.0d | 2.0            | BigDecimal.class
        5.0d | 2.0d | 2.5            | BigDecimal.class
        0.0d | 1.0d | 0              | BigDecimal.class
        4    | 2    | 2              | RationalNumber.class
    }

    def "division by zero throws exception"() {
        given:
        def numberSystem = new DefaultNumberSystem()

        when:
        numberSystem.divide(1, 0)

        then:
        thrown(IllegalArgumentException)
    }

    def "divide numbers with remainder - result of type #expectedType"() {
        given:
        def numberSystem = new DefaultNumberSystem()

        when:
        Number[] results = numberSystem.divideAndRemainder(x, y, roundToZero)

        then:
        results.length == 2
        results[0] == quotient
        results[1] == remainder

        where:
        x    | y    | roundToZero | quotient | remainder
        4.0f | 2.0d | false       | 2.0      | 0
        5.0d | 2.0d | false       | 2.0      | 1
        5.0d | 2.0d | true        | 2.0      | 1
        0.0d | 1.0d | true        | 0.0      | 0
        5    | 2    | true        | 2        | 1
    }

    def "division with remainder by zero throws exception"() {
        given:
        def numberSystem = new DefaultNumberSystem()

        when:
        numberSystem.divideAndRemainder(1, 0, true)

        then:
        thrown(ArithmeticException)
    }

    def "reciprocal"() {
        given:
        def numberSystem = new DefaultNumberSystem()

        when:
        def result = numberSystem.reciprocal(value)

        then:
        result.toDouble() == expectedResult

        where:
        value                  | expectedResult
        1                      | 1
        2                      | 0.5
        0.5                    | 2
        0.5f                   | 2
        RationalNumber.of(2.0) | RationalNumber.of(0.5)
    }

    def "signum"() {
        given:
        def numberSystem = new DefaultNumberSystem()

        when:
        def result = numberSystem.signum(value)

        then:
        result == expectedResult

        where:
        value | expectedResult
        1     | 1
        -1    | -1
        0     | 0
        2     | 1
        -2    | -1
    }

    def "abs"() {
        given:
        def numberSystem = new DefaultNumberSystem()

        when:
        def result = numberSystem.abs(value)

        then:
        result == expectedResult

        where:
        value | expectedResult
        1     | 1
        -1    | 1
        0     | 0
        2     | 2
        -2    | 2
    }

    def "negate"() {
        given:
        def numberSystem = new DefaultNumberSystem()

        when:
        def result = numberSystem.negate(value)

        then:
        result == expectedResult

        where:
        value | expectedResult
        1     | -1
        -1    | 1
        0     | 0
        2     | -2
        -2    | 2
    }

    def "power"() {
        given:
        def numberSystem = new DefaultNumberSystem()

        when:
        def result = numberSystem.power(base, exp)

        then:
        closeTo(result, expectedResult, 0.0001)

        where:
        base | exp | expectedResult
        1    | 1   | 1
        2    | 1   | 2
        1    | 2   | 1
        2    | 2   | 4
        2    | 3   | 8
        2.5  | 2   | 6.25
    }

    def "exp"() {
        given:
        def numberSystem = new DefaultNumberSystem()

        when:
        def result = numberSystem.exp(exp)

        then:
        closeTo(result, expectedResult, 0.0001)

        where:
        exp | expectedResult
        0   | 1
        1   | Math.E
    }

    def "log"() {
        given:
        def numberSystem = new DefaultNumberSystem()

        when:
        def result = numberSystem.log(exp)

        then:
        closeTo(result, expectedResult, 0.0001)

        where:
        exp    | expectedResult
        1      | 0
        Math.E | 1
    }

    def "narrow"() {
        given:
        def numberSystem = new DefaultNumberSystem()

        when:
        def result = numberSystem.narrow(value as Number)

        then:
        expectedType.isInstance(result)

        where:
        value                           | expectedType
        Double.valueOf(3.14159)         | Double.class
        new BigDecimal("3.14159")       | BigDecimal.class
        new BigDecimal(Short.MAX_VALUE) | Integer.class
        Long.MAX_VALUE                  | Long.class
    }

    def "compare"() {
        given:
        def numberSystem = new DefaultNumberSystem()

        when:
        def result = numberSystem.compare(x, y)

        then:
        result == expected

        where:
        x | y   | expected
        1 | 1.0 | 0
        2 | 1.0 | 1
        1 | 2.0 | -1
    }

    def "isZero"() {
        given:
        def numberSystem = new DefaultNumberSystem()

        when:
        def result = numberSystem.isZero(value)

        then:
        result == expected

        where:
        value               | expected
        Double.valueOf(0)   | true
        new BigDecimal("0") | true
        new BigDecimal(0)   | true
        0                   | true
        Double.valueOf(1)   | false
        new BigDecimal("1") | false
        new BigDecimal(1)   | false
        1                   | false
    }

    def "isOne"() {
        given:
        def numberSystem = new DefaultNumberSystem()

        when:
        def result = numberSystem.isOne(value)

        then:
        result == expected

        where:
        value               | expected
        Double.valueOf(0)   | false
        new BigDecimal("0") | false
        new BigDecimal(0)   | false
        0                   | false
        Double.valueOf(1)   | true
        new BigDecimal("1") | true
        new BigDecimal(1)   | true
        1                   | true
    }

    def "isLessThanOne"() {
        given:
        def numberSystem = new DefaultNumberSystem()

        when:
        def result = numberSystem.isLessThanOne(value)
        println(value + " " + result)

        then:
        result == expected

        where:
        value                 | expected
        Double.valueOf(0)     | true
        new BigDecimal("0")   | true
        new BigDecimal(0)     | true
        0                     | true
        Double.valueOf(1.0)   | false
        new BigDecimal("1.0") | false
        new BigDecimal(1)     | false
        1                     | false
    }

    def "isInteger"() {
        given:
        def numberSystem = new DefaultNumberSystem()

        when:
        def result = numberSystem.isInteger(value)

        then:
        result == expected

        where:
        value                  | expected
        Double.valueOf(1)      | true
        new BigDecimal("-2")   | true
        new BigDecimal(3)      | true
        -4                     | true
        Double.valueOf(1.1)    | false
        new BigDecimal("-1.2") | false
        new BigDecimal(1.3)    | false
        -1.4                   | false
    }

    def "division result"() {
        expect:
        NumberSystem.DivisionResult.of(1, 2) == NumberSystem.DivisionResult.of(1, 2)
    }

    def "compare numbers"() {
        expect:
        new DefaultNumberSystem().equals(x, y) == expected
        where:
        x                  | y                  | expected
        Integer.valueOf(1) | Integer.valueOf(1) | true
        Integer.valueOf(1) | Integer.valueOf(2) | false
        Integer.valueOf(1) | Long.valueOf(3)    | false

    }
}
