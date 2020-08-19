package com.github.raynigon.unit_api_starter.jackson.annotation;

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
