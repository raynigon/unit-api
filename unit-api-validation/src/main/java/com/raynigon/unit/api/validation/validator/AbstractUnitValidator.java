package com.raynigon.unit.api.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.BiPredicate;

abstract public class AbstractUnitValidator<A extends Annotation>
        implements ConstraintValidator<A, Quantity<?>> {

    private Unit<?> unit = null;
    private double value = 0.0;

    @Override
    public void initialize(A constraintAnnotation) {
        unit = createUnit(getUnit(constraintAnnotation));
        value = getValue(constraintAnnotation);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected boolean check(ConstraintValidatorContext context, Quantity<?> quantity, BiPredicate<Double, Double> comparator) {
        if (quantity == null) return true;
        if (unit == null) return false;
        double quantityAsNumber = ((Quantity) quantity)
                .to(unit).getValue().doubleValue();
        return comparator.test(quantityAsNumber, value);
    }

    protected abstract Class<? extends Unit<?>> getUnit(A constraintAnnotation);

    protected abstract double getValue(A constraintAnnotation);

    public static Unit<?> createUnit(Class<? extends Unit<?>> unitType) {
        try {
            Constructor<? extends Unit<?>> ctor = unitType.getConstructor();
            return ctor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new IllegalArgumentException("Unable to create Unit " + unitType, e);
        }
    }
}
