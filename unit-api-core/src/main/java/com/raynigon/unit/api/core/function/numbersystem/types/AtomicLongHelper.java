package com.raynigon.unit.api.core.function.numbersystem.types;

import com.raynigon.unit.api.core.function.RationalNumber;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongHelper implements TypedNumberHelper<AtomicLong>, IntegerNumberHelper<AtomicLong> {

    private final BigDecimalHelper bdh = new BigDecimalHelper();

    @Override
    public RationalNumber reciprocal(AtomicLong number) {
        return RationalNumber.of(BigInteger.ONE, BigInteger.valueOf(number.get()));
    }

    @Override
    public Number negate(AtomicLong number) {
        long value = number.get();
        if (value == Long.MIN_VALUE) {
            return BigInteger.valueOf(value).negate(); // widen to BigInteger
        }
        return -value;
    }

    @Override
    public int signum(AtomicLong number) {
        return Long.signum(number.get());
    }

    @Override
    public Number abs(AtomicLong number) {
        long value = number.get();
        if (Long.MIN_VALUE == value) {
            return BigInteger.valueOf(value).abs(); // widen to BigInteger
        }
        return Math.abs(value);
    }

    @Override
    public Number exp(AtomicLong number) {
        return bdh.exp(toBigDecimal(number));
    }

    @Override
    public Number log(AtomicLong number) {
        return bdh.log(toBigDecimal(number));
    }

    @Override
    public boolean isZero(AtomicLong number) {
        return number.get() == 0L;
    }

    @Override
    public boolean isOne(AtomicLong number) {
        return number.get() == 1L;
    }

    @Override
    public boolean isLessThanOne(AtomicLong number) {
        return number.get() < 1L;
    }

    @Override
    public boolean isInteger(AtomicLong number) {
        return true;
    }

    @Override
    public Number narrow(AtomicLong number) {
        final int total_bits_required = bitLengthOfInteger(number);

        // check whether we have enough bits to store the result into an int
        if (total_bits_required < 31) {
            return number.intValue();
        }

        // check whether we have enough bits to store the result into a long
        if (total_bits_required < 63) {
            return number;
        }

        return number; // cannot narrow down
    }

    @Override
    public Number power(AtomicLong number, int exponent) {
        final BigInteger bigInt = toBigInteger(number);
        if (exponent > 0) {
            return bigInt.pow(exponent);
        }
        return RationalNumber.ofInteger(bigInt).pow(exponent);
    }

    @Override
    public BigDecimal toBigDecimal(AtomicLong number) {
        return new BigDecimal(number.get());
    }

    @Override
    public BigInteger toBigInteger(AtomicLong number) {
        return BigInteger.valueOf(number.get());
    }

    private int bitLengthOfInteger(AtomicLong number) {
        long value = number.get();
        if (value == Long.MIN_VALUE) {
            return 63;
        } else {
            int leadingZeros = Long.numberOfLeadingZeros(Math.abs(value));
            return 64 - leadingZeros;
        }
    }
}
