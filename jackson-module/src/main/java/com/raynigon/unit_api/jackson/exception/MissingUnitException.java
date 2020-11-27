package com.raynigon.unit_api.jackson.exception;

import com.fasterxml.jackson.databind.JsonMappingException;

import javax.measure.Quantity;
import java.io.Closeable;

public class MissingUnitException extends JsonMappingException {

    public MissingUnitException(Closeable processor, String unitName) {
        super(processor, "Unknown Unit: " + unitName);
    }

    @SuppressWarnings("rawtypes")
    public MissingUnitException(Closeable processor, Class<Quantity> quantityType) {
        super(processor, "Missing Unit for Quantity Type: " + quantityType);
    }
}
