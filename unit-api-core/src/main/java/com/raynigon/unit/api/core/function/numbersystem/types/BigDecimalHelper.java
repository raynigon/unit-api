package com.raynigon.unit.api.core.function.numbersystem.types;

import com.raynigon.unit.api.core.function.RationalNumber;
import org.apache.commons.lang3.NotImplementedException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigDecimalHelper implements TypedNumberHelper<BigDecimal> {

    @Override
    public RationalNumber reciprocal(BigDecimal number) {
        return RationalNumber.of(number).reciprocal();
    }

    @Override
    public BigDecimal negate(BigDecimal number) {
        return number.negate();
    }

    @Override
    public int signum(BigDecimal number) {
        return number.signum();
    }

    @Override
    public BigDecimal abs(BigDecimal number) {
        return number.abs();
    }

    @Override
    public BigDecimal exp(BigDecimal number) {
        return BigDecimal.valueOf(Math.exp(number.doubleValue()));
    }

    @Override
    public BigDecimal log(BigDecimal number) {
        int precision = number.precision();
        MathContext context = new MathContext(precision, RoundingMode.HALF_DOWN);
        return log(number, context);
    }

    @Override
    public boolean isZero(BigDecimal number) {
        return BigDecimal.ZERO.compareTo(number) == 0;
    }

    @Override
    public boolean isOne(BigDecimal number) {
        return BigDecimal.ONE.compareTo(number) == 0;
    }

    @Override
    public boolean isLessThanOne(BigDecimal number) {
        return BigDecimal.ONE.compareTo(number) > 0;
    }

    @Override
    public boolean isInteger(BigDecimal number) {
        // see https://stackoverflow.com/questions/1078953/check-if-bigdecimal-is-integer-value
        if (number.scale() <= 0) {
            return true;
        }
        try {
            number.toBigIntegerExact();
            return true;
        } catch (ArithmeticException ex) {
            return false;
        }
    }

    @Override
    public BigDecimal toBigDecimal(BigDecimal number) {
        return number;
    }

    /**
     * Calculates the natural logarithm using a Taylor sequence.
     *
     * @param input the input big decimal > 0
     * @return the natural logarithm
     */
    public static BigDecimal log(BigDecimal input, MathContext context) {

        BigDecimal two = new BigDecimal("2", context);

        BigDecimal inputMinus = input.subtract(BigDecimal.ONE, context);
        BigDecimal inputPlus = input.add(BigDecimal.ONE, context);
        BigDecimal y = inputMinus.divide(inputPlus, context);

        BigDecimal result = new BigDecimal("0", context);
        BigDecimal last = new BigDecimal(result.toString(), context);

        int k = 0;
        while (true) {

            BigDecimal argumentOne = BigDecimal.ONE
                    .divide(BigDecimal.ONE.add(two.multiply(new BigDecimal(k), context), context), context);
            BigDecimal argumentTwo = y.pow(k * 2, context);
            result = result.add(argumentOne.multiply(argumentTwo, context), context);

            if (last.equals(result)) {
                break;
            }

            last = new BigDecimal(result.toString(), context);
            k++;
        }

        return y.multiply(two, context).multiply(result, context);
    }
}
