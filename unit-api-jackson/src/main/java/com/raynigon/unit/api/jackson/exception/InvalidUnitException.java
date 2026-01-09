package com.raynigon.unit.api.jackson.exception;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.exc.InvalidFormatException;

import javax.measure.Quantity;

public class InvalidUnitException extends InvalidFormatException {

    public InvalidUnitException(JsonParser p, String unitName) {
        super(p, "Unknown Unit: " + unitName, unitName, Quantity.class);
    }

    @SuppressWarnings("rawtypes")
    public InvalidUnitException(JsonParser p, Class<Quantity> quantityType) {
        super(p, "Unknown Unit for Quantity Type: " + quantityType, quantityType, Quantity.class);
    }
}
