package com.raynigon.unit_api.jackson.annotation;

import com.raynigon.unit_api.core.annotation.QuantityShape;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

@Target({METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonUnit {

    /**
     * AliasFor(value="unit")
     * Specifies the unit in which the Quantity is serialized/deserialized
     * @return the unit which should be used for serialization/deserialization
     */
    String value() default "";

    /**
     * Specifies the unit in which the Quantity is serialized/deserialized
     * @return the unit which should be used for serialization/deserialization
     */
    String unit() default "";

    /**
     * Serialize the Quantity as String
     * @return the shape which should be used for serialization/deserialization
     */
    QuantityShape shape() default QuantityShape.NUMBER;
}
