package com.raynigon.unit.api.core.function.numbersystem.exception;

import com.raynigon.unit.api.core.function.NumberSystem;

public class UnsupportedNumberTypeException extends IllegalArgumentException {

    public UnsupportedNumberTypeException(Number number, Class<? extends NumberSystem> clazz) {
        super(String.format(
                "Unsupported number value '%s' of type '%s' in number system '%s'",
                number.toString(), number.getClass(), clazz.getName()));
    }
}
