package com.raynigon.unit.api.jackson.exception;

import tools.jackson.databind.DatabindException;

import javax.measure.Unit;
import java.io.Closeable;

public class IncompatibleUnitException extends DatabindException {

    public IncompatibleUnitException(Closeable processor, Unit<?> systemUnit, Unit<?> annotatedUnit) {
        super(processor, "Annotated Unit " + annotatedUnit + " is not compatible to " + systemUnit);
    }
}
