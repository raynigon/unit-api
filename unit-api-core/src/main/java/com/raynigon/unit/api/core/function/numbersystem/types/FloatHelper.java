package com.raynigon.unit.api.core.function.numbersystem.types;

import com.raynigon.unit.api.core.function.CalculusUtils;
import com.raynigon.unit.api.core.function.RationalNumber;
import com.raynigon.unit.api.core.function.numbersystem.DefaultNumberSystem;
import com.raynigon.unit.api.core.function.numbersystem.exception.UnsupportedNumberValueException;
import org.apache.commons.lang3.NotImplementedException;

import java.math.BigDecimal;

public class FloatHelper implements TypedNumberHelper<Float> {

    private BigIntegerHelper bigIntegerHelper = new BigIntegerHelper();

    @Override
    public RationalNumber reciprocal(Float number) {
        return RationalNumber.of(number.doubleValue()).reciprocal();
    }

    @Override
    public Float negate(Float number) {
        return -number;
    }

    @Override
    public int signum(Float number) {
        return (int) Math.signum(number);
    }

    @Override
    public Float abs(Float number) {
        return Math.abs(number);
    }

    @Override
    public Double exp(Float number) {
        return Math.exp(number);
    }

    @Override
    public Double log(Float number) {
        return Math.log(number);
    }

    @Override
    public boolean isZero(Float number) {
        return abs(number) == 0.0f;
    }

    @Override
    public boolean isOne(Float number) {
        return number == 1.0f;
    }

    @Override
    public boolean isLessThanOne(Float number) {
        return number < 1.0f;
    }

    @Override
    public boolean isInteger(Float number) {
        // see
        // https://stackoverflow.com/questions/15963895/how-to-check-if-a-double-value-has-no-decimal-part
        return number % 1 == 0;
    }

    @Override
    public Number narrow(Float number) {
        if (!Float.isFinite(number)) {
            throw new UnsupportedNumberValueException(number, DefaultNumberSystem.class);
        }
        if (number % 1 == 0) {
            // float represents an integer
            return bigIntegerHelper.narrow(BigDecimal.valueOf(number).toBigIntegerExact());
        }
        return number;
    }

    @Override
    public Number power(Float number, int exponent) {
        return toBigDecimal(number).pow(exponent, CalculusUtils.MATH_CONTEXT);
    }

    @Override
    public BigDecimal toBigDecimal(Float number) {
        return BigDecimal.valueOf(number);
    }
}
