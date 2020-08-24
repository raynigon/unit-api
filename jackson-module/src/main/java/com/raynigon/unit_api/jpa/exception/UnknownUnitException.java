package com.raynigon.unit_api.jpa.exception;

import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.Closeable;

public class UnknownUnitException extends JsonMappingException {


    public UnknownUnitException(Closeable processor, String unitName) {
        super(processor, "Unknown Unit: "+unitName);
    }
}
