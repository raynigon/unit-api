package com.raynigon.unit.api.jackson.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.raynigon.unit.api.core.annotation.QuantityShape;
import com.raynigon.unit.api.core.units.general.IUnit;

import javax.measure.quantity.Volume;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonUnit {

    /**
     * AliasFor(value="unit") Specifies the unit in which the Quantity is serialized/deserialized
     *
     * @return the unit which should be used for serialization/deserialization
     */
    Class<? extends IUnit<?>> value() default NoneUnit.class;

    /**
     * Specifies the unit in which the Quantity is serialized/deserialized
     *
     * @return the unit which should be used for serialization/deserialization
     */
    Class<? extends IUnit<?>> unit() default NoneUnit.class;

    /**
     * Serialize the Quantity as String
     *
     * @return the shape which should be used for serialization/deserialization
     */
    QuantityShape shape() default QuantityShape.NUMBER;

    interface NoneUnit extends IUnit<Volume>{}
}
