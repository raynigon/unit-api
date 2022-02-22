package com.raynigon.unit.api.core.function.numbersystem.exception;

import com.raynigon.unit.api.core.function.NumberSystem;

public class UnsupportedNumberTypeException extends IllegalArgumentException{

    public UnsupportedNumberTypeException(Number number, Class<? extends NumberSystem> clazz) {

    }
}
