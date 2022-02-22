package com.raynigon.unit.api.core.function.numbersystem.types;

import com.raynigon.unit.api.core.function.RationalNumber;
import org.apache.commons.lang3.NotImplementedException;

import java.math.BigDecimal;
import java.math.BigInteger;

public class LongHelper implements TypedNumberHelper<Long>, IntegerNumberHelper<Long> {

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
    public Number narrow(Long number) {
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
    public Number power(Long number, int exponent) {
        final BigInteger bigInt = toBigInteger(number);
        if (exponent > 0) {
            return bigInt.pow(exponent);
        }
        return RationalNumber.ofInteger(bigInt).pow(exponent);
    }

    @Override
    public BigDecimal toBigDecimal(Long number) {
        return new BigDecimal(number);
    }

    @Override
    public BigInteger toBigInteger(Long number) {
        return BigInteger.valueOf(number);
    }

    private int bitLengthOfInteger(long number) {
        if (number == Long.MIN_VALUE) {
            return 63;
        } else {
            int leadingZeros = Long.numberOfLeadingZeros(Math.abs(number));
            return 64 - leadingZeros;
        }
    }
}
