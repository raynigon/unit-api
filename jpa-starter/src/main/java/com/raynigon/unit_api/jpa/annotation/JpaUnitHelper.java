package com.raynigon.unit_api.jpa.annotation;

import com.raynigon.unit_api.core.annotation.QuantityShape;

import javax.measure.Quantity;

/**
 * Support a simple usage of the @JpaUnit properties
 */
public class JpaUnitHelper {

    /**
     * Determine the value of the shape property
     *
     * @param jpaUnit  the annotation which should be processed
     * @return the specified Shape or QuantityShape.NUMBER if no shape was specified
     */
    public static QuantityShape getShape(JpaUnit jpaUnit) {
        if (jpaUnit == null) return QuantityShape.NUMBER;
        return jpaUnit.shape();
    }

    /**
     * Determine the value of the unit property
     *
     * @param jpaUnit  the annotation which should be processed
     * @return the name of the specified unit or null if no unit was specified
     */
    public static String getUnitName(JpaUnit jpaUnit) {
        if (jpaUnit == null) return null;
        if (!jpaUnit.value().equals("")) return jpaUnit.value();
        if (!jpaUnit.unit().equals("")) return jpaUnit.unit();
        return null;
    }

    /**
     * Determine the value of the unit property
     *
     * @param jpaUnit  the annotation which should be processed
     * @return the name of the specified unit or null if no unit was specified
     */
    public static Class<? extends Quantity<?>> getQuantityType(JpaUnit jpaUnit) {
        if (jpaUnit == null) return null;
        Class<? extends Quantity<?>> quantityType = jpaUnit.quantityType();
        if (quantityType.equals(NoneQuantity.class)) return null;
        if (!Quantity.class.isAssignableFrom(quantityType)) return null;
        return quantityType;
    }
}
