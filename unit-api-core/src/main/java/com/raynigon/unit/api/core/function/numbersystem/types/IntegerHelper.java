package com.raynigon.unit.api.core.function.numbersystem.types;

import com.raynigon.unit.api.core.function.RationalNumber;
import org.apache.commons.lang3.NotImplementedException;

import java.math.BigDecimal;
import java.math.BigInteger;

public class IntegerHelper implements TypedNumberHelper<Integer>, IntegerNumberHelper<Integer> {
    @Override
    public RationalNumber reciprocal(Integer number) {
        return RationalNumber.of(BigInteger.ONE, BigInteger.valueOf((int) number));
    }

    @Override
    public Number negate(Integer number) {
        if (number == Integer.MIN_VALUE) {
            return -number.longValue(); // widen to long
        }
        return -number;
    }

    @Override
    public int signum(Integer number) {
        return Integer.signum(number);
    }

    @Override
    public Number abs(Integer number) {
        if (Integer.MIN_VALUE == number) {
            return Math.abs(number.longValue()); // widen to long
        }
        return Math.abs(number);
    }

    @Override
    public Number exp(Integer number) {
        return Math.exp(number);
    }

    @Override
    public Double log(Integer number) {
        return Math.log(number);
    }

    @Override
    public boolean isZero(Integer number) {
        return number == 0;
    }

    @Override
    public boolean isOne(Integer number) {
        return number == 1;
    }

    @Override
    public boolean isLessThanOne(Integer number) {
        return number < 1;
    }

    @Override
    public boolean isInteger(Integer number) {
        return true;
    }

    @Override
    public Number narrow(Integer number) {
        return number;
    }

    @Override
    public Number power(Integer number, int exponent) {
        final BigInteger bigInt = toBigInteger(number);
        if (exponent > 0) {
            return bigInt.pow(exponent);
        }
        return RationalNumber.ofInteger(bigInt).pow(exponent);
    }

    @Override
    public BigInteger toBigInteger(Integer number) {
        return BigInteger.valueOf(number);
    }

    @Override
    public BigDecimal toBigDecimal(Integer number) {
        return new BigDecimal(number);
    }
}
