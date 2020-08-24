package com.raynigon.unit_api.jackson.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JsonUnit {

    /**
     * Specifies the unit in which the Quantity is serialized/deserialized
     */
    String unit() default "";

    /**
     * Serialize the Quantity as String
     */
    QuantityShape shape() default QuantityShape.NUMBER;
}