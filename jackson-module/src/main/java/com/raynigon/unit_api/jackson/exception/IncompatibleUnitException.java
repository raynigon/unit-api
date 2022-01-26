package com.raynigon.unit_api.jackson.exception;

import com.fasterxml.jackson.databind.JsonMappingException;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.io.Closeable;

public class IncompatibleUnitException extends JsonMappingException {

    public IncompatibleUnitException(Closeable processor, Unit<?> systemUnit, Unit<?> annotatedUnit) {
        super(processor, "Annotated Unit " + annotatedUnit + " is not compatible to " + systemUnit);
    }
}
