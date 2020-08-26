package com.raynigon.unit_api.jackson.annotation;

import com.raynigon.unit_api.core.annotation.QuantityShape;
import com.raynigon.unit_api.core.service.UnitResolverService;
import com.raynigon.unit_api.jackson.exception.UnknownUnitException;

import javax.measure.Unit;

/**
 * Support a simple usage of the @JsonUnit properties
 * */
public class JsonUnitHelper {

    /**
     * Determine the value of the shape property
     * @param jsonUnit  the annotation which should be processed
     * @return the specified Shape or QuantityShape.NUMBER if no shape was specified
     * */
    public static QuantityShape getShape(JsonUnit jsonUnit){
        if (jsonUnit == null) return QuantityShape.NUMBER;
        return jsonUnit.shape();
    }

    /**
     * Determine the value of the unit property
     * @param jsonUnit  the annotation which should be processed
     * @return the name of the specified unit or null if no unit was specified
     * */
    public static String getUnitName(JsonUnit jsonUnit) {
        if (jsonUnit == null) return null;
        if (!jsonUnit.value().equals("")) return jsonUnit.value();
        if (!jsonUnit.unit().equals("")) return jsonUnit.unit();
        return null;
    }
}
