package com.raynigon.unit.api.core.function.numbersystem.exception;

public class UnexpectedCodeReachException extends IllegalStateException {

    public UnexpectedCodeReachException() {
        super("Implementation Error: Code was reached that is expected unreachable");
    }
}
