package com.raynigon.unit.api.core.function.numbersystem.types;

import com.raynigon.unit.api.core.function.RationalNumber;

import java.math.BigDecimal;
import java.math.BigInteger;

public class LongHelper implements TypedNumberHelper<Long> {

    private final BigDecimalHelper bdh = new BigDecimalHelper();

    @Override
    public RationalNumber reciprocal(Long number) {
        return RationalNumber.of(BigInteger.ONE, BigInteger.valueOf(number));
    }

    @Override
    public Number negate(Long number) {
        if (number == Long.MIN_VALUE) {
            return BigInteger.valueOf(number).negate(); // widen to BigInteger
        }
        return -number;
    }

    @Override
    public int signum(Long number) {
        return Long.signum(number);
    }

    @Override
    public Number abs(Long number) {
        if (Long.MIN_VALUE == number) {
            return BigInteger.valueOf(number).abs(); // widen to BigInteger
        }
        return Math.abs(number);
    }

    @Override
    public Number exp(Long number) {
        return bdh.exp(toBigDecimal(number));
    }

    @Override
    public Number log(Long number) {
        return bdh.log(toBigDecimal(number));
    }

    @Override
    public boolean isZero(Long number) {
        return number == 0L;
    }

    @Override
    public boolean isOne(Long number) {
        return number == 1L;
    }

    @Override
    public boolean isLessThanOne(Long number) {
        return number < 1L;
    }

    @Override
    public boolean isInteger(Long number) {
        return true;
    }

    @Override
    public BigDecimal toBigDecimal(Long number) {
        return new BigDecimal(number);
    }
}
