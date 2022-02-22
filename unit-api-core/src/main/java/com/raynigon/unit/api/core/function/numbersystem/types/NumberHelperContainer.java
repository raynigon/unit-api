package com.raynigon.unit.api.core.function.numbersystem.types;

import com.raynigon.unit.api.core.function.RationalNumber;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class NumberHelperContainer<T extends Number> {

    private TypedNumberHelper<T> instance;
    private T value;

    public RationalNumber reciprocal() {
        return instance.reciprocal(value);
    }

    public Number negate() {
        return instance.negate(value);
    }

    public int signum() {
        return instance.signum(value);
    }

    public Number abs() {
        return instance.abs(value);
    }

    public Number exp() {
        return instance.exp(value);
    }

    public Number log() {
        return instance.log(value);
    }

    public boolean isZero() {
        return instance.isZero(value);
    }

    public boolean isOne() {
        return instance.isOne(value);
    }

    public boolean isLessThanOne() {
        return instance.isLessThanOne(value);
    }

    public boolean isInteger() {
        return instance.isInteger(value);
    }

    public BigDecimal toBigDecimal() {
        return instance.toBigDecimal(value);
    }

    public Number power(int exponent) {
        // x^0 == 1, for any x!=0
        if (exponent == 0) {
            if (isZero()) {
                throw new ArithmeticException("0^0 is not defined");
            }
            return 1;
        }
        // x^1 == x, for any x
        if (exponent == 1) {
            return value;
        }
        return instance.power(value, exponent);
    }

    public Number narrow() {
        return instance.narrow(value);
    }
}
