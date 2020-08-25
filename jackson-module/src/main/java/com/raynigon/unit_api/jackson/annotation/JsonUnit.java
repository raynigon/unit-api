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
     * Specifies the unit in which the Quantity is serialized/deserialized
     */
    String unit() default "";

    /**
     * Serialize the Quantity as String
     */
    QuantityShape shape() default QuantityShape.NUMBER;
}
