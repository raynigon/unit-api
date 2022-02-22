package com.raynigon.unit.api.core.function.numbersystem.types;

import com.raynigon.unit.api.core.function.RationalNumber;

import java.math.BigDecimal;

public interface TypedNumberHelper<T extends Number> {

    RationalNumber reciprocal(T number);

    Number negate(T number);

    int signum(T number);

    Number abs(T number);

    /**
     * Raises e to the power of the input big decimal.
     *
     * @param number the input power
     * @return the result
     */
    Number exp(T number);

    /**
     * Calculates the natural logarithm using a Taylor sequqnce.
     *
     * @param number the input number &gt; 0
     * @return the natural logarithm
     */
    Number log(T number);

    boolean isZero(T number);

    boolean isOne(T number);

    boolean isLessThanOne(T number);

    boolean isInteger(T number);

    BigDecimal toBigDecimal(T number);

    Number narrow(T number);

    Number power(T number, int exponent);
}
