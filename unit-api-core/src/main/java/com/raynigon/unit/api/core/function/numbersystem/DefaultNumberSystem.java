/*
 * Units of Measurement Reference Implementation
 * Copyright (c) 2005-2020, Jean-Marie Dautelle, Werner Keil, Otavio Santana.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 *    and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of JSR-385, Indriya nor the names of their contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.raynigon.unit.api.core.function.numbersystem;

import com.raynigon.unit.api.core.function.CalculusUtils;
import com.raynigon.unit.api.core.function.NumberSystem;
import com.raynigon.unit.api.core.function.RationalNumber;
import com.raynigon.unit.api.core.function.numbersystem.exception.UnexpectedCodeReachException;
import com.raynigon.unit.api.core.function.numbersystem.exception.UnsupportedNumberTypeException;
import com.raynigon.unit.api.core.function.numbersystem.exception.UnsupportedNumberValueException;
import com.raynigon.unit.api.core.function.numbersystem.types.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.UnaryOperator;

import static com.raynigon.unit.api.core.function.numbersystem.DefaultNumberType.*;

/**
 * {@link NumberSystem} implementation to support Java's built-in {@link Number}s and the {@link
 * RationalNumber} type.
 *
 * @author Andi Huber
 * @since 2.0
 */
public class DefaultNumberSystem implements NumberSystem {

    @Override
    public Number add(Number x, Number y) {

        final DefaultNumberType type_x = DefaultNumberType.valueOf(x);
        final DefaultNumberType type_y = DefaultNumberType.valueOf(y);

        final boolean reorder_args = type_y.ordinal() > type_x.ordinal();

        return reorder_args ? addWideAndNarrow(type_y, y, x) : addWideAndNarrow(type_x, x, y);
    }

    @Override
    public Number subtract(Number x, Number y) {
        return add(x, negate(y));
    }

    @Override
    public Number multiply(Number x, Number y) {

        final DefaultNumberType type_x = DefaultNumberType.valueOf(x);
        final DefaultNumberType type_y = DefaultNumberType.valueOf(y);

        final boolean reorder_args = type_y.ordinal() > type_x.ordinal();

        return reorder_args ? multiplyWideAndNarrow(type_y, y, x) : multiplyWideAndNarrow(type_x, x, y);
    }

    @Override
    public Number divide(Number x, Number y) {
        return multiply(x, reciprocal(y));
    }

    @Override
    public Number[] divideAndRemainder(Number x, Number y, boolean roundRemainderTowardsZero) {

        final int sign_x = signum(x);
        final int sign_y = signum(y);

        final int sign = sign_x * sign_y;
        // handle corner cases when x or y are zero
        if (sign == 0) {
            if (sign_y == 0) {
                throw new ArithmeticException("division by zero");
            }
            if (sign_x == 0) {
                return new Number[]{0, 0};
            }
        }

        final Number absX = abs(x);
        final Number absY = abs(y);

        final DefaultNumberType type_x = DefaultNumberType.valueOf(absX);
        final DefaultNumberType type_y = DefaultNumberType.valueOf(absY);

        // if x and y are both integer types than we can calculate integer results,
        // otherwise we resort to BigDecimal
        final boolean yieldIntegerResult = type_x.isIntegerOnly() && type_y.isIntegerOnly();

        if (yieldIntegerResult) {

            final BigInteger integer_x = integerToBigInteger(absX);
            final BigInteger integer_y = integerToBigInteger(absY);

            final BigInteger[] divAndRemainder = integer_x.divideAndRemainder(integer_y);

            return applyToArray(divAndRemainder, number -> copySignTo(sign, (BigInteger) number));

        } else {

            final MathContext mathContext =
                    new MathContext(CalculusUtils.MATH_CONTEXT.getPrecision(), RoundingMode.FLOOR);

            final BigDecimal decimal_x =
                    (type_x == DefaultNumberType.RATIONAL)
                            ? ((RationalNumber) absX).bigDecimalValue()
                            : toBigDecimal(absX);
            final BigDecimal decimal_y =
                    (type_y == DefaultNumberType.RATIONAL)
                            ? ((RationalNumber) absY).bigDecimalValue()
                            : toBigDecimal(absY);

            final BigDecimal[] divAndRemainder = decimal_x.divideAndRemainder(decimal_y, mathContext);

            if (roundRemainderTowardsZero) {
                return new Number[]{
                        copySignTo(sign, divAndRemainder[0]), copySignTo(sign, divAndRemainder[1].toBigInteger())
                };

            } else {
                return applyToArray(divAndRemainder, number -> copySignTo(sign, (BigDecimal) number));
            }
        }
    }

    @Override
    public Number reciprocal(Number number) {
        return createHelper(number).reciprocal();
    }

    @Override
    public int signum(Number number) {
        return createHelper(number).signum();
    }

    @Override
    public Number abs(Number number) {
        return createHelper(number).abs();
    }

    @Override
    public Number negate(Number number) {
        return createHelper(number).negate();
    }

    @Override
    public Number power(Number number, int exponent) {
        return createHelper(number).power(exponent);
    }

    @Override
    public Number exp(Number number) {
        return createHelper(number).exp();
    }

    @Override
    public Number log(Number number) {
        return createHelper(number).log();
    }

    @Override
    public Number narrow(Number number) {
        return createHelper(number).narrow();
    }

    @Override
    public int compare(Number x, Number y) {

        final DefaultNumberType type_x = DefaultNumberType.valueOf(x);
        final DefaultNumberType type_y = DefaultNumberType.valueOf(y);

        final boolean reorder_args = type_y.ordinal() > type_x.ordinal();

        return reorder_args ? -compareWideVsNarrow(type_y, y, x) : compareWideVsNarrow(type_x, x, y);
    }

    @Override
    public boolean isZero(Number number) {
        return createHelper(number).isZero();
    }

    @Override
    public boolean isOne(Number number) {
        return createHelper(number).isOne();
    }

    @Override
    public boolean isLessThanOne(Number number) {
        return createHelper(number).isLessThanOne();
    }

    @Override
    public boolean isInteger(Number number) {
        return createHelper(number).isInteger();
    }

    // -- HELPER

    private int bitLengthOfInteger(Number number) {
        if (number instanceof BigInteger) {
            return ((BigInteger) number).bitLength();
        }
        long long_value = number.longValue();

        if (long_value == Long.MIN_VALUE) {
            return 63;
        } else {
            int leadingZeros = Long.numberOfLeadingZeros(Math.abs(long_value));
            return 64 - leadingZeros;
        }
    }

    private BigInteger integerToBigInteger(Number number) {
        if (number instanceof BigInteger) {
            return (BigInteger) number;
        }
        return BigInteger.valueOf(number.longValue());
    }

    private BigDecimal toBigDecimal(Number number) {
        return createHelper(number).toBigDecimal();
    }

    private Number addWideAndNarrow(DefaultNumberType wideType, Number wide, Number narrow) {

        if (wideType.isIntegerOnly()) {
            // at this point we know, that narrow must also be an integer-only type
            if (wide instanceof BigInteger) {
                return ((BigInteger) wide).add(integerToBigInteger(narrow));
            }

            // at this point we know, that 'wide' and 'narrow' are one of {(Atomic)Long, (Atomic)Integer,
            // Short, Byte}

            // +1 carry, not including sign
            int total_bits_required = Math.max(bitLengthOfInteger(wide), bitLengthOfInteger(narrow)) + 1;

            // check whether we have enough bits to store the result into a long
            if (total_bits_required < 63) {
                return wide.longValue() + narrow.longValue();
            }

            return integerToBigInteger(wide).add(integerToBigInteger(narrow));
        }

        if (wide instanceof RationalNumber) {

            // at this point we know, that narrow must either be rational or an integer-only type
            if (narrow instanceof RationalNumber) {
                return ((RationalNumber) wide).add((RationalNumber) narrow);
            }

            return ((RationalNumber) wide).add(RationalNumber.ofInteger(integerToBigInteger(narrow)));
        }

        // at this point we know, that wide is one of {BigDecimal, Double, Float}

        if (wide instanceof BigDecimal) {

            if (narrow instanceof BigDecimal) {
                return ((BigDecimal) wide).add((BigDecimal) narrow, CalculusUtils.MATH_CONTEXT);
            }

            if (narrow instanceof Double || narrow instanceof Float) {
                return ((BigDecimal) wide)
                        .add(BigDecimal.valueOf(narrow.doubleValue()), CalculusUtils.MATH_CONTEXT);
            }

            if (narrow instanceof RationalNumber) {
                // TODO[220] can we do better than that, eg. by converting BigDecimal to RationalNumber
                return ((BigDecimal) wide).add(((RationalNumber) narrow).bigDecimalValue());
            }

            // at this point we know, that 'narrow' is one of {(Atomic)Long, (Atomic)Integer, Short, Byte}
            return ((BigDecimal) wide).add(BigDecimal.valueOf(narrow.longValue()));
        }

        // at this point we know, that wide is one of {Double, Float}

        if (narrow instanceof Double || narrow instanceof Float) {
            // converting to BigDecimal, because especially fractional addition is sensitive to precision
            // loss
            return BigDecimal.valueOf(wide.doubleValue()).add(BigDecimal.valueOf(narrow.doubleValue()));
        }

        if (narrow instanceof RationalNumber) {
            // TODO[220] can we do better than that, eg. by converting BigDecimal to RationalNumber
            return BigDecimal.valueOf(wide.doubleValue())
                    .add(((RationalNumber) narrow).bigDecimalValue());
        }

        if (narrow instanceof BigInteger) {
            return BigDecimal.valueOf(wide.doubleValue()).add(new BigDecimal((BigInteger) narrow));
        }

        // at this point we know, that 'narrow' is one of {(Atomic)Long, (Atomic)Integer, Short, Byte}
        return BigDecimal.valueOf(wide.doubleValue()).add(BigDecimal.valueOf(narrow.longValue()));
    }

    private Number multiplyWideAndNarrow(DefaultNumberType wideType, Number wide, Number narrow) {

        if (wideType.isIntegerOnly()) {
            // at this point we know, that narrow must also be an integer-only type
            if (wide instanceof BigInteger) {
                return ((BigInteger) wide).multiply(integerToBigInteger(narrow));
            }

            // at this point we know, that 'wide' and 'narrow' are one of {(Atomic)Long, (Atomic)Integer,
            // Short, Byte}

            int total_bits_required =
                    bitLengthOfInteger(wide) + bitLengthOfInteger(narrow); // not including sign

            // check whether we have enough bits to store the result into a long
            if (total_bits_required < 63) {
                return wide.longValue() * narrow.longValue();
            }

            return integerToBigInteger(wide).multiply(integerToBigInteger(narrow));
        }

        if (wide instanceof RationalNumber) {

            // at this point we know, that narrow must either be rational or an integer-only type
            if (narrow instanceof RationalNumber) {
                return ((RationalNumber) wide).multiply((RationalNumber) narrow);
            }

            return ((RationalNumber) wide)
                    .multiply(RationalNumber.ofInteger(integerToBigInteger(narrow)));
        }

        // at this point we know, that wide is one of {BigDecimal, Double, Float}

        if (wide instanceof BigDecimal) {

            if (narrow instanceof BigDecimal) {
                return ((BigDecimal) wide).multiply((BigDecimal) narrow, CalculusUtils.MATH_CONTEXT);
            }

            if (narrow instanceof Double || narrow instanceof Float) {
                return ((BigDecimal) wide)
                        .multiply(BigDecimal.valueOf(narrow.doubleValue()), CalculusUtils.MATH_CONTEXT);
            }

            if (narrow instanceof RationalNumber) {
                // TODO[220] can we do better than that, eg. by converting BigDecimal to RationalNumber
                return ((BigDecimal) wide).multiply(((RationalNumber) narrow).bigDecimalValue());
            }

            // at this point we know, that 'narrow' is one of {(Atomic)Long, (Atomic)Integer, Short, Byte}
            return ((BigDecimal) wide).multiply(BigDecimal.valueOf(narrow.longValue()));
        }

        // at this point we know, that wide is one of {Double, Float}

        if (narrow instanceof Double || narrow instanceof Float) {
            // not converting to BigDecimal, because fractional multiplication is not sensitive to
            // precision loss
            return wide.doubleValue() * narrow.doubleValue();
        }

        if (narrow instanceof RationalNumber) {
            // TODO[220] can we do better than that, eg. by converting BigDecimal to RationalNumber
            return BigDecimal.valueOf(wide.doubleValue())
                    .multiply(((RationalNumber) narrow).bigDecimalValue());
        }

        if (narrow instanceof BigInteger) {
            return BigDecimal.valueOf(wide.doubleValue()).multiply(new BigDecimal((BigInteger) narrow));
        }

        // at this point we know, that 'narrow' is one of {(Atomic)Long, (Atomic)Integer, Short, Byte}
        return BigDecimal.valueOf(wide.doubleValue()).multiply(BigDecimal.valueOf(narrow.longValue()));
    }

    private int compareWideVsNarrow(DefaultNumberType wideType, Number wide, Number narrow) {

        if (wideType.isIntegerOnly()) {
            // at this point we know, that narrow must also be an integer-only type
            if (wide instanceof BigInteger) {
                return ((BigInteger) wide).compareTo(integerToBigInteger(narrow));
            }

            // at this point we know, that 'wide' and 'narrow' are one of {(Atomic)Long, (Atomic)Integer,
            // Short, Byte}
            return Long.compare(wide.longValue(), narrow.longValue());
        }

        if (wide instanceof RationalNumber) {

            // at this point we know, that narrow must either be rational or an integer-only type
            if (narrow instanceof RationalNumber) {
                return ((RationalNumber) wide).compareTo((RationalNumber) narrow);
            }

            return ((RationalNumber) wide)
                    .compareTo(RationalNumber.ofInteger(integerToBigInteger(narrow)));
        }

        // at this point we know, that wide is one of {BigDecimal, Double, Float}

        if (wide instanceof BigDecimal) {

            if (narrow instanceof BigDecimal) {
                return ((BigDecimal) wide).compareTo((BigDecimal) narrow);
            }

            if (narrow instanceof Double || narrow instanceof Float) {
                return ((BigDecimal) wide).compareTo(BigDecimal.valueOf(narrow.doubleValue()));
            }

            if (narrow instanceof RationalNumber) {
                // TODO[220] can we do better than that, eg. by converting BigDecimal to RationalNumber
                return ((BigDecimal) wide).compareTo(((RationalNumber) narrow).bigDecimalValue());
            }

            // at this point we know, that 'narrow' is one of {(Atomic)Long, (Atomic)Integer, Short, Byte}
            return ((BigDecimal) wide).compareTo(BigDecimal.valueOf(narrow.longValue()));
        }

        // at this point we know, that wide is one of {Double, Float}

        if (narrow instanceof Double || narrow instanceof Float) {
            return Double.compare(wide.doubleValue(), narrow.doubleValue());
        }

        if (narrow instanceof RationalNumber) {
            // TODO[220] can we do better than that, eg. by converting BigDecimal to RationalNumber
            return BigDecimal.valueOf(wide.doubleValue())
                    .compareTo(((RationalNumber) narrow).bigDecimalValue());
        }

        if (narrow instanceof BigInteger) {
            return BigDecimal.valueOf(wide.doubleValue()).compareTo(new BigDecimal((BigInteger) narrow));
        }

        // at this point we know, that 'narrow' is one of {(Atomic)Long, (Atomic)Integer, Short, Byte}
        return BigDecimal.valueOf(wide.doubleValue()).compareTo(BigDecimal.valueOf(narrow.longValue()));
    }

    // only for non-zero sign
    private static BigInteger copySignTo(int sign, BigInteger absNumber) {
        if (sign == -1) {
            return absNumber.negate();
        }
        return absNumber;
    }

    // only for non-zero sign
    private static BigDecimal copySignTo(int sign, BigDecimal absNumber) {
        if (sign == -1) {
            return absNumber.negate();
        }
        return absNumber;
    }

    private static Number[] applyToArray(Number[] array, UnaryOperator<Number> operator) {
        // only ever used for length=2
        return new Number[]{operator.apply(array[0]), operator.apply(array[1])};
    }

    @SuppressWarnings("PMD.NPathComplexity")
    private NumberHelperContainer<?> createHelper(Number number) {
        if (number instanceof BigInteger) {
            return new NumberHelperContainer<>(BIG_INTEGER.helper(), (BigInteger) number);
        }
        if (number instanceof BigDecimal) {
            return new NumberHelperContainer<>(BIG_DECIMAL.helper(), (BigDecimal) number);
        }
        if (number instanceof RationalNumber) {
            return new NumberHelperContainer<>(RATIONAL.helper(), (RationalNumber) number);
        }
        if (number instanceof Double) {
            return new NumberHelperContainer<>(DOUBLE_BOXED.helper(), (Double) number);
        }
        if (number instanceof Float) {
            return new NumberHelperContainer<>(FLOAT_BOXED.helper(), (Float) number);
        }
        if (number instanceof Long) {
            return new NumberHelperContainer<>(LONG_BOXED.helper(), (Long) number);
        }
        if (number instanceof Integer) {
            return new NumberHelperContainer<>(INTEGER_BOXED.helper(), (Integer) number);
        }
        if (number instanceof Short) {
            return new NumberHelperContainer<>(SHORT_BOXED.helper(), (Short) number);
        }
        if (number instanceof Byte) {
            return new NumberHelperContainer<>(BYTE_BOXED.helper(), (byte) number);
        }
        if (number instanceof AtomicInteger) {
            return new NumberHelperContainer<>(INTEGER_ATOMIC.helper(), (AtomicInteger) number);
        }
        if (number instanceof AtomicLong) {
            return new NumberHelperContainer<>(LONG_ATOMIC.helper(), (AtomicLong) number);
        }
        throw new UnsupportedNumberTypeException(number, this.getClass());
    }
}
