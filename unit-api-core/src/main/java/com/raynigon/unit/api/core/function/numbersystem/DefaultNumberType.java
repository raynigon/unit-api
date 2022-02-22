package com.raynigon.unit.api.core.function.numbersystem;

import com.raynigon.unit.api.core.function.RationalNumber;
import com.raynigon.unit.api.core.function.numbersystem.types.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In order of increasing number type 'widening'.
 */
public enum DefaultNumberType {

    // integer types
    BYTE_BOXED(true, Byte.class, (byte) 1, (byte) 0, new ByteHelper()),
    SHORT_BOXED(true, Short.class, (short) 1, (short) 0, new ShortHelper()),
    INTEGER_BOXED(true, Integer.class, 1, 0, new IntegerHelper()),
    INTEGER_ATOMIC(true, AtomicInteger.class, 1, 0, new AtomicIntegerHelper()),
    LONG_BOXED(true, Long.class, 1L, 0L, new LongHelper()),
    LONG_ATOMIC(true, AtomicLong.class, 1L, 0, new AtomicLongHelper()),
    BIG_INTEGER(true, BigInteger.class, BigInteger.ONE, BigInteger.ZERO, new BigIntegerHelper()),

    // rational types
    RATIONAL(false, RationalNumber.class, RationalNumber.ONE, RationalNumber.ZERO, new RationalNumberHelper()),

    // fractional types
    FLOAT_BOXED(false, Float.class, 1.f, 0.f, new FloatHelper()),
    DOUBLE_BOXED(false, Double.class, 1.d, 0.d, new DoubleHelper()),
    BIG_DECIMAL(false, BigDecimal.class, BigDecimal.ONE, BigDecimal.ZERO, new BigDecimalHelper()),
    ;
    private final boolean integerOnly;
    private final Class<? extends Number> type;
    public final Number one;
    public final Number zero;
    private final TypedNumberHelper<?> helper;

    DefaultNumberType(boolean integerOnly, Class<? extends Number> type, Number one, Number zero, TypedNumberHelper<?> helper) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(one);
        Objects.requireNonNull(zero);
        Objects.requireNonNull(helper);
        this.integerOnly = integerOnly;
        this.type = type;
        this.one = one;
        this.zero = zero;
        this.helper = helper;
    }

    @SuppressWarnings("unchecked")
    public <T extends Number> TypedNumberHelper<T> helper() {
        return (TypedNumberHelper<T>) helper;
    }

    public boolean isIntegerOnly() {
        return integerOnly;
    }

    @SuppressWarnings("unused")
    public Class<? extends Number> getType() {
        return type;
    }

    // 'hardcoded' for performance reasons
    @SuppressWarnings("PMD.NPathComplexity")
    public static DefaultNumberType valueOf(Number number) {
        if (number instanceof Long) {
            return LONG_BOXED;
        }
        if (number instanceof AtomicLong) {
            return LONG_ATOMIC;
        }
        if (number instanceof Integer) {
            return INTEGER_BOXED;
        }
        if (number instanceof AtomicInteger) {
            return INTEGER_ATOMIC;
        }
        if (number instanceof Double) {
            return DOUBLE_BOXED;
        }
        if (number instanceof Short) {
            return SHORT_BOXED;
        }
        if (number instanceof Byte) {
            return BYTE_BOXED;
        }
        if (number instanceof Float) {
            return FLOAT_BOXED;
        }
        if (number instanceof BigDecimal) {
            return BIG_DECIMAL;
        }
        if (number instanceof BigInteger) {
            return BIG_INTEGER;
        }
        if (number instanceof RationalNumber) {
            return RATIONAL;
        }
        final String msg = String.format("Unsupported number type '%s'", number.getClass().getName());
        throw new IllegalArgumentException(msg);
    }
}
