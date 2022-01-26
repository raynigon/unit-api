package com.raynigon.unit.api.jackson.exception;

import com.fasterxml.jackson.databind.JsonMappingException;

import javax.measure.Quantity;
import java.io.Closeable;

public class UnknownUnitException extends JsonMappingException {

    public UnknownUnitException(Closeable processor, String unitName) {
        super(processor, "Unknown Unit: " + unitName);
    }

    @SuppressWarnings("rawtypes")
    public UnknownUnitException(Closeable processor, Class<Quantity> quantityType) {
        super(processor, "Unknown Unit for Quantity Type: " + quantityType);
    }
}
