package com.raynigon.unit.api.core.function.numbersystem.types;

import com.raynigon.unit.api.core.function.RationalNumber;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerHelper implements TypedNumberHelper<AtomicInteger>, IntegerNumberHelper<AtomicInteger> {
    @Override
    public RationalNumber reciprocal(AtomicInteger number) {
        return RationalNumber.of(BigInteger.ONE, BigInteger.valueOf(number.get()));
    }

    @Override
    public Number negate(AtomicInteger number) {
        int value = number.get();
        if (value == Integer.MIN_VALUE) {
            return -number.longValue(); // widen to long
        }
        return -value;
    }

    @Override
    public int signum(AtomicInteger number) {
        return Integer.signum(number.get());
    }

    @Override
    public Number abs(AtomicInteger number) {
        int value = number.get();
        if (Integer.MIN_VALUE == value) {
            return Math.abs(number.longValue()); // widen to long
        }
        return Math.abs(value);
    }

    @Override
    public Number exp(AtomicInteger number) {
        return Math.exp(number.get());
    }

    @Override
    public Double log(AtomicInteger number) {
        return Math.log(number.get());
    }

    @Override
    public boolean isZero(AtomicInteger number) {
        return number.get() == 0;
    }

    @Override
    public boolean isOne(AtomicInteger number) {
        return number.get() == 1;
    }

    @Override
    public boolean isLessThanOne(AtomicInteger number) {
        return number.get() < 1;
    }

    @Override
    public boolean isInteger(AtomicInteger number) {
        return true;
    }

    @Override
    public Number narrow(AtomicInteger number) {
        return number;
    }

    @Override
    public Number power(AtomicInteger number, int exponent) {
        final BigInteger bigInt = toBigInteger(number);
        if (exponent > 0) {
            return bigInt.pow(exponent);
        }
        return RationalNumber.ofInteger(bigInt).pow(exponent);
    }

    @Override
    public BigInteger toBigInteger(AtomicInteger number) {
        return BigInteger.valueOf(number.get());
    }

    @Override
    public BigDecimal toBigDecimal(AtomicInteger number) {
        return new BigDecimal(number.get());
    }
}
