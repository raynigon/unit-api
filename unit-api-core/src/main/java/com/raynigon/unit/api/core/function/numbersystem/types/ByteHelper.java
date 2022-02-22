package com.raynigon.unit.api.core.function.numbersystem.types;

import com.raynigon.unit.api.core.function.RationalNumber;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ByteHelper implements TypedNumberHelper<Byte>, IntegerNumberHelper<Byte> {
    @Override
    public RationalNumber reciprocal(Byte number) {
        return RationalNumber.of(BigInteger.ONE, BigInteger.valueOf((int) number));
    }

    @Override
    public Number negate(Byte number) {
        return -number.intValue();
    }

    @Override
    public int signum(Byte number) {
        return Integer.signum(number);
    }

    @Override
    public Number abs(Byte number) {
        return Math.abs(number.intValue());
    }

    @Override
    public Number exp(Byte number) {
        return Math.exp(number);
    }

    @Override
    public Double log(Byte number) {
        return Math.log(number);
    }

    @Override
    public boolean isZero(Byte number) {
        return number == 0;
    }

    @Override
    public boolean isOne(Byte number) {
        return number == 1;
    }

    @Override
    public boolean isLessThanOne(Byte number) {
        return number < 1;
    }

    @Override
    public boolean isInteger(Byte number) {
        return true;
    }

    @Override
    public Number narrow(Byte number) {
        return number;
    }

    @Override
    public Number power(Byte number, int exponent) {
        final BigInteger bigInt = toBigInteger(number);
        if (exponent > 0) {
            return bigInt.pow(exponent);
        }
        return RationalNumber.ofInteger(bigInt).pow(exponent);
    }

    @Override
    public BigInteger toBigInteger(Byte number) {
        return BigInteger.valueOf(number);
    }

    @Override
    public BigDecimal toBigDecimal(Byte number) {
        return new BigDecimal(number);
    }
}
