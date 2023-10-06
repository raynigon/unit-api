package com.raynigon.unit.api.validation.validator;

import com.raynigon.unit.api.validation.annotation.UnitMax;
import jakarta.validation.ConstraintValidatorContext;

import javax.measure.Quantity;
import javax.measure.Unit;

public class UnitMaxValidator extends AbstractUnitValidator<UnitMax> {

    @Override
    protected Class<? extends Unit<?>> getUnit(UnitMax constraintAnnotation) {
        return constraintAnnotation.unit();
    }

    @Override
    protected double getValue(UnitMax constraintAnnotation) {
        return constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Quantity<?> value, ConstraintValidatorContext context) {
        return check(value, ((q, v) -> q <= v));
    }
}
