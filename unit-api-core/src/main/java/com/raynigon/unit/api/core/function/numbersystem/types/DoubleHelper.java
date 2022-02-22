package com.raynigon.unit.api.core.function.numbersystem.types;

import com.raynigon.unit.api.core.function.CalculusUtils;
import com.raynigon.unit.api.core.function.RationalNumber;
import com.raynigon.unit.api.core.function.numbersystem.DefaultNumberSystem;
import com.raynigon.unit.api.core.function.numbersystem.exception.UnsupportedNumberValueException;
import org.apache.commons.lang3.NotImplementedException;

import java.math.BigDecimal;

public class DoubleHelper implements TypedNumberHelper<Double> {

    private BigIntegerHelper bigIntegerHelper = new BigIntegerHelper();

    @Override
    public RationalNumber reciprocal(Double number) {
        return RationalNumber.of(number).reciprocal();
    }

    @Override
    public Double negate(Double number) {
        return -number;
    }

    @Override
    public int signum(Double number) {
        return (int) Math.signum(number);
    }

    @Override
    public Double abs(Double number) {
        return Math.abs(number);
    }

    @Override
    public Double exp(Double number) {
        return Math.exp(number);
    }

    @Override
    public Double log(Double number) {
        return Math.log(number);
    }

    @Override
    public boolean isZero(Double number) {
        return abs(number) == 0.0;
    }

    @Override
    public boolean isOne(Double number) {
        return number == 1.0;
    }

    @Override
    public boolean isLessThanOne(Double number) {
        return number < 1.0;
    }

    @Override
    public boolean isInteger(Double number) {
        // see
        // https://stackoverflow.com/questions/15963895/how-to-check-if-a-double-value-has-no-decimal-part
        return number % 1 == 0;
    }

    @Override
    public Number narrow(Double number) {
        if (!Double.isFinite(number)) {
            throw new UnsupportedNumberValueException(number, DefaultNumberSystem.class);
        }
        if (number % 1 == 0) {
            // double represents an integer
            return bigIntegerHelper.narrow(BigDecimal.valueOf(number).toBigIntegerExact());
        }
        return number;
    }

    @Override
    public Number power(Double number, int exponent) {
        return toBigDecimal(number).pow(exponent, CalculusUtils.MATH_CONTEXT);
    }

    @Override
    public BigDecimal toBigDecimal(Double number) {
        return BigDecimal.valueOf(number);
    }
}
