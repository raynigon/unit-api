package com.raynigon.unit.api.core.function.numbersystem.types;

import com.raynigon.unit.api.core.function.RationalNumber;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ShortHelper implements TypedNumberHelper<Short>, IntegerNumberHelper<Short> {
    @Override
    public RationalNumber reciprocal(Short number) {
        return RationalNumber.of(BigInteger.ONE, BigInteger.valueOf((int) number));
    }

    @Override
    public Number negate(Short number) {
        return -number.intValue();
    }

    @Override
    public int signum(Short number) {
        return Integer.signum(number);
    }

    @Override
    public Number abs(Short number) {
        return Math.abs(number.intValue());
    }

    @Override
    public Number exp(Short number) {
        return Math.exp(number);
    }

    @Override
    public Double log(Short number) {
        return Math.log(number);
    }

    @Override
    public boolean isZero(Short number) {
        return number == 0;
    }

    @Override
    public boolean isOne(Short number) {
        return number == 1;
    }

    @Override
    public boolean isLessThanOne(Short number) {
        return number < 1;
    }

    @Override
    public boolean isInteger(Short number) {
        return true;
    }

    @Override
    public Number narrow(Short number) {
        return number;
    }

    @Override
    public Number power(Short number, int exponent) {
        final BigInteger bigInt = toBigInteger(number);
        if (exponent > 0) {
            return bigInt.pow(exponent);
        }
        return RationalNumber.ofInteger(bigInt).pow(exponent);
    }

    @Override
    public BigInteger toBigInteger(Short number) {
        return BigInteger.valueOf(number);
    }

    @Override
    public BigDecimal toBigDecimal(Short number) {
        return new BigDecimal(number);
    }
}
