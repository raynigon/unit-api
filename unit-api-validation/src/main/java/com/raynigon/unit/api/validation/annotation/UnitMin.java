package com.raynigon.unit.api.validation.annotation;

import com.raynigon.unit.api.validation.validator.UnitMinValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import javax.measure.Unit;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must be a number whose value must be higher or
 * equal to the specified minimum.
 * <p>
 * Supported types are:
 * <ul>
 *     <li>{@code Quantity}</li>
 * </ul>
 * Note that {@code double} and {@code float} are not supported due to rounding errors
 * (some providers might provide some approximative support).
 * <p>
 * {@code null} elements are considered valid.
 *
 * @author Simon Schneider
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {UnitMinValidator.class})
public @interface UnitMin {

    String message() default "{com.raynigon.unit.api.validation.UnitMin.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * The {@code String} representation of the min value according to the
     * {@code BigDecimal} string representation.
     *
     * @return value the element must be higher or equal to
     */
    double value();

    /**
     * The unit which should be used to compare the value to
     */
    Class<? extends Unit<?>> unit();
}

