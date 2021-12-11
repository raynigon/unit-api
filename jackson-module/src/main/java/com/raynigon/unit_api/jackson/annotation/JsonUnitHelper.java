package com.raynigon.unit_api.jackson.annotation;

import com.raynigon.unit_api.core.annotation.QuantityShape;
import com.raynigon.unit_api.core.units.general.IUnit;
import com.raynigon.unit_api.jackson.exception.InitializationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Support a simple usage of the @JsonUnit properties
 */
public class JsonUnitHelper {

    /**
     * Determine the value of the shape property
     *
     * @param jsonUnit the annotation which should be processed
     * @return the specified Shape or QuantityShape.NUMBER if no shape was specified
     */
    public static QuantityShape getShape(JsonUnit jsonUnit) {
        if (jsonUnit == null) return QuantityShape.NUMBER;
        return jsonUnit.shape();
    }

    /**
     * Determine the value of the unit property
     *
     * @param jsonUnit the annotation which should be processed
     * @return the name of the specified unit or null if no unit was specified
     */
    public static IUnit<?> getUnitInstance(JsonUnit jsonUnit) {
        if (jsonUnit == null) return null;
        if (!JsonUnit.NoneUnit.class.equals(jsonUnit.value())) return createUnit(jsonUnit.value());
        if (!JsonUnit.NoneUnit.class.equals(jsonUnit.unit())) return createUnit(jsonUnit.unit());
        return null;
    }

    private static IUnit<?> createUnit(Class<? extends IUnit<?>> unitType) {
        try {
            Constructor<? extends IUnit<?>> ctor = unitType.getConstructor();
            return ctor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new InitializationException("Unable to create Unit " + unitType, e);
        }
    }
}
