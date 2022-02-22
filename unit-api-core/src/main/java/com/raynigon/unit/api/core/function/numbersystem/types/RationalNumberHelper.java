package com.raynigon.unit.api.core.function.numbersystem.types;

import com.raynigon.unit.api.core.function.RationalNumber;
import org.apache.commons.lang3.NotImplementedException;

import java.math.BigDecimal;
import java.math.BigInteger;

public class RationalNumberHelper implements TypedNumberHelper<RationalNumber> {

    private final BigDecimalHelper bdh = new BigDecimalHelper();
    private final BigIntegerHelper bih = new BigIntegerHelper();

    @Override
    public RationalNumber reciprocal(RationalNumber number) {
        return number.reciprocal();
    }

    @Override
    public RationalNumber negate(RationalNumber number) {
        return number.negate();
    }

    @Override
    public int signum(RationalNumber number) {
        return number.signum();
    }

    @Override
    public RationalNumber abs(RationalNumber number) {
        return number.abs();
    }

    @Override
    public Number exp(RationalNumber number) {
        return bdh.exp(toBigDecimal(number));
    }

    @Override
    public Number log(RationalNumber number) {
        return bdh.log(toBigDecimal(number));
    }

    @Override
    public boolean isZero(RationalNumber number) {
        return number.getDividend().compareTo(BigInteger.ZERO) == 0;
    }

    @Override
    public boolean isOne(RationalNumber number) {
        return number.getDividend().compareTo(number.getDivisor()) == 0;
    }

    @Override
    public boolean isLessThanOne(RationalNumber number) {
        // TODO this could be done more exactly
        return number.doubleValue() < 1.0;
    }

    @Override
    public boolean isInteger(RationalNumber number) {
        return number.isInteger();
    }

    @Override
    public Number narrow(RationalNumber number) {
        // divisor is ONE
        if (BigInteger.ONE.compareTo(number.getDivisor()) == 0) {
            return bih.narrow(number.getDividend());
        }

        BigInteger modulo = number.getDividend().mod(number.getDivisor());
        // dividend is multiplier of divisor
        if (BigInteger.ZERO.compareTo(modulo) == 0) {
            return bih.narrow(number.getDividend().divide(number.getDivisor()));
        }

        return number;
    }

    @Override
    public Number power(RationalNumber number, int exponent) {
        return number.pow(exponent);
    }

    @Override
    public BigDecimal toBigDecimal(RationalNumber number) {
        return number.bigDecimalValue();
    }
}
