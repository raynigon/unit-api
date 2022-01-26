package com.raynigon.unit.api.jpa.annotation;

import com.raynigon.unit.api.core.annotation.QuantityShape;
import com.raynigon.unit.api.core.units.general.IUnit;

import javax.measure.Quantity;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Support a simple usage of the @JpaUnit properties.
 */
public class JpaUnitHelper {

    /**
     * Determine the value of the shape property
     *
     * @param jpaUnit the annotation which should be processed
     * @return the specified Shape or QuantityShape.NUMBER if no shape was specified
     */
    public static QuantityShape getShape(JpaUnit jpaUnit) {
        if (jpaUnit == null) {
            return QuantityShape.NUMBER;
        }
        return jpaUnit.shape();
    }

    /**
     * Determine the value of the unit property
     *
     * @param jpaUnit the annotation which should be processed
     * @return the name of the specified unit or null if no unit was specified
     */
    public static IUnit<?> getUnitInstance(JpaUnit jpaUnit) {
        if (jpaUnit == null) {
            return null;
        }
        if (!jpaUnit.value().equals(JpaUnit.NoneUnit.class)) {
            return createUnit(jpaUnit.value());
        }
        if (!jpaUnit.unit().equals(JpaUnit.NoneUnit.class)) {
            return createUnit(jpaUnit.unit());
        }
        return null;
    }

    /**
     * Determine the value of the unit property
     *
     * @param jpaUnit the annotation which should be processed
     * @return the name of the specified unit or null if no unit was specified
     */
    public static Class<? extends Quantity<?>> getQuantityType(JpaUnit jpaUnit) {
        if (jpaUnit == null) {
            return null;
        }
        Class<? extends Quantity<?>> quantityType = jpaUnit.quantityType();
        if (quantityType.equals(NoneQuantity.class)) {
            return null;
        }
        if (!Quantity.class.isAssignableFrom(quantityType)) {
            return null;
        }
        return quantityType;
    }

    private static IUnit<?> createUnit(Class<? extends IUnit<?>> unitType) {
        try {
            Constructor<? extends IUnit<?>> ctor = unitType.getConstructor();
            return ctor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            // TODO log error("Unable to create Unit " + unitType, e);
            return null;
        }
    }
}
