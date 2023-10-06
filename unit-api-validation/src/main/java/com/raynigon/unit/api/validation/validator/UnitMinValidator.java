package com.raynigon.unit.api.validation.validator;

import com.raynigon.unit.api.validation.annotation.UnitMin;
import jakarta.validation.ConstraintValidatorContext;

import javax.measure.Quantity;
import javax.measure.Unit;

public class UnitMinValidator extends AbstractUnitValidator<UnitMin> {

    @Override
    protected Class<? extends Unit<?>> getUnit(UnitMin constraintAnnotation) {
        return constraintAnnotation.unit();
    }

    @Override
    protected double getValue(UnitMin constraintAnnotation) {
        return constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Quantity<?> value, ConstraintValidatorContext context) {
        return check(value, ((q, v) -> q >= v));
    }
}
