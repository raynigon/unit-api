package com.raynigon.unit.api.core.function.numbersystem.types;

import com.raynigon.unit.api.core.function.RationalNumber;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigIntegerHelper implements TypedNumberHelper<BigInteger> {

    private final BigDecimalHelper bdh;

    public BigIntegerHelper() {
        bdh = new BigDecimalHelper();
    }

    public BigIntegerHelper(BigDecimalHelper bdh) {
        this.bdh = bdh;
    }

    @Override
    public RationalNumber reciprocal(BigInteger number) {
        return RationalNumber.of(BigInteger.ONE, number);
    }

    @Override
    public BigInteger negate(BigInteger number) {
        return number.negate();
    }

    @Override
    public int signum(BigInteger number) {
        return number.signum();
    }

    @Override
    public BigInteger abs(BigInteger number) {
        return number.abs();
    }

    @Override
    public Number exp(BigInteger number) {
        return bdh.exp(toBigDecimal(number));
    }

    @Override
    public Number log(BigInteger number) {
        return bdh.log(toBigDecimal(number));
    }

    @Override
    public boolean isZero(BigInteger number) {
        return BigInteger.ZERO.equals(number);
    }

    @Override
    public boolean isOne(BigInteger number) {
        return BigInteger.ONE.equals(number);
    }

    @Override
    public boolean isLessThanOne(BigInteger number) {
        return BigInteger.ONE.compareTo(number) > 0;
    }

    @Override
    public boolean isInteger(BigInteger number) {
        return true;
    }

    @Override
    public Number narrow(BigInteger number) {
        final int total_bits_required = number.bitLength();

        // check whether we have enough bits to store the result into an int
        if (total_bits_required < 31) {
            return number.intValue();
        }

        // check whether we have enough bits to store the result into a long
        if (total_bits_required < 63) {
            return number.longValue();
        }

        return number; // cannot narrow down
    }

    @Override
    public Number power(BigInteger number, int exponent) {
        return number.pow(exponent);
    }

    @Override
    public BigDecimal toBigDecimal(BigInteger number) {
        return new BigDecimal(number);
    }
}
