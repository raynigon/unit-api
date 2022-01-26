package com.raynigon.unit.api.jackson.annotation;

import com.raynigon.unit.api.core.io.QuantityReader;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

@Target({METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonQuantityReader {

    Class<? extends QuantityReader> value();
}
