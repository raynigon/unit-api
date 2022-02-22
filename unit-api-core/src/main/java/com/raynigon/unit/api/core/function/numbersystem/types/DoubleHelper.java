package com.raynigon.unit.api.core.function.numbersystem.types;

import com.raynigon.unit.api.core.function.RationalNumber;
import org.apache.commons.lang3.NotImplementedException;

import java.math.BigDecimal;

public class DoubleHelper implements TypedNumberHelper<Double> {

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
    public BigDecimal toBigDecimal(Double number) {
        return new BigDecimal(number);
    }
}
