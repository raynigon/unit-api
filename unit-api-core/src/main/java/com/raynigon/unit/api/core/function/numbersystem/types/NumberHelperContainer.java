package com.raynigon.unit.api.core.function.numbersystem.types;

import com.raynigon.unit.api.core.function.RationalNumber;
import lombok.AllArgsConstructor;

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
}
