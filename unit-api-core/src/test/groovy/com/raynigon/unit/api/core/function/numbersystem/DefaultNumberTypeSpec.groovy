package com.raynigon.unit.api.core.function.numbersystem

import com.raynigon.unit.api.core.function.RationalNumber
import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

class DefaultNumberTypeSpec extends Specification {

    def 'value of number to type'() {
        expect:
        DefaultNumberType.valueOf(number) == value

        where:
        number                 | value
        new Long(1)            | DefaultNumberType.LONG_BOXED
        new AtomicLong(1)      | DefaultNumberType.LONG_ATOMIC
        new Integer(1)         | DefaultNumberType.INTEGER_BOXED
        new AtomicInteger(1)   | DefaultNumberType.INTEGER_ATOMIC
        new Double(1)          | DefaultNumberType.DOUBLE_BOXED
        new Short(1 as short)  | DefaultNumberType.SHORT_BOXED
        new Byte(1 as byte)    | DefaultNumberType.BYTE_BOXED
        new Float(1)           | DefaultNumberType.FLOAT_BOXED
        new BigDecimal(1)      | DefaultNumberType.BIG_DECIMAL
        BigInteger.valueOf(1)  | DefaultNumberType.BIG_INTEGER
        RationalNumber.of(1.0) | DefaultNumberType.RATIONAL
    }

    def 'value of unknown number'() {
        given:
        def number = new UnknownNumber()

        when:
        DefaultNumberType.valueOf(number)

        then:
        thrown(IllegalArgumentException)
    }

    @Unroll
    def 'integerOnly for type #value is #expected'() {
        expect:
        value.integerOnly == expected

        where:
        value                            | expected
        DefaultNumberType.LONG_BOXED     | true
        DefaultNumberType.LONG_ATOMIC    | true
        DefaultNumberType.INTEGER_BOXED  | true
        DefaultNumberType.INTEGER_ATOMIC | true
        DefaultNumberType.DOUBLE_BOXED   | false
        DefaultNumberType.SHORT_BOXED    | true
        DefaultNumberType.BYTE_BOXED     | true
        DefaultNumberType.FLOAT_BOXED    | false
        DefaultNumberType.BIG_DECIMAL    | false
        DefaultNumberType.BIG_INTEGER    | true
        DefaultNumberType.RATIONAL       | false
    }

    @Unroll
    def 'number class from type: #value'() {
        expect:
        value.getType() == numberType

        where:
        value                            | numberType
        DefaultNumberType.LONG_BOXED     | Long
        DefaultNumberType.LONG_ATOMIC    | AtomicLong
        DefaultNumberType.INTEGER_BOXED  | Integer
        DefaultNumberType.INTEGER_ATOMIC | AtomicInteger
        DefaultNumberType.DOUBLE_BOXED   | Double
        DefaultNumberType.SHORT_BOXED    | Short
        DefaultNumberType.BYTE_BOXED     | Byte
        DefaultNumberType.FLOAT_BOXED    | Float
        DefaultNumberType.BIG_DECIMAL    | BigDecimal
        DefaultNumberType.BIG_INTEGER    | BigInteger
        DefaultNumberType.RATIONAL       | RationalNumber
    }

    class UnknownNumber extends Number {

        @Override
        int intValue() {
            return 0
        }

        @Override
        long longValue() {
            return 0
        }

        @Override
        float floatValue() {
            return 0
        }

        @Override
        double doubleValue() {
            return 0
        }
    }
}
