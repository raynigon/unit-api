package com.github.raynigon.unit_api_starter.jpa.annotation;

import com.raynigon.unit_api.core.annotation.QuantityShape;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JpaUnit {

    /**
     * Specifies the unit in which the Quantity is serialized/deserialized
     */
    String unit() default "";

    /**
     * Serialize the Quantity as String
     */
    QuantityShape shape() default QuantityShape.NUMBER;
}
