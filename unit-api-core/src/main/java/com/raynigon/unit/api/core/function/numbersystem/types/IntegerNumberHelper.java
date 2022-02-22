package com.raynigon.unit.api.core.function.numbersystem.types;

import java.math.BigInteger;

public interface IntegerNumberHelper<T extends Number> {

    BigInteger toBigInteger(T number);

    int bitLength(T number);
}
