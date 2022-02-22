package com.raynigon.unit.api.core.function.numbersystem.types;

import com.raynigon.unit.api.core.function.RationalNumber;

import java.math.BigDecimal;
import java.math.BigInteger;

public class RationalNumberHelper implements TypedNumberHelper<RationalNumber> {

    private final BigDecimalHelper bdh = new BigDecimalHelper();

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
    public BigDecimal toBigDecimal(RationalNumber number) {
        return number.bigDecimalValue();
    }
}
