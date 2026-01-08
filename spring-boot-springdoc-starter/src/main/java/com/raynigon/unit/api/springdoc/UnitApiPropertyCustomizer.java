package com.raynigon.unit.api.springdoc;

import com.fasterxml.jackson.databind.JavaType;
import com.raynigon.unit.api.core.annotation.QuantityShape;
import com.raynigon.unit.api.core.service.UnitsApiService;
import com.raynigon.unit.api.jackson.annotation.JsonUnit;
import com.raynigon.unit.api.jackson.annotation.JsonUnitHelper;
import com.raynigon.unit.api.validation.annotation.UnitMax;
import com.raynigon.unit.api.validation.annotation.UnitMin;
import com.raynigon.unit.api.validation.validator.AbstractUnitValidator;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.models.media.NumberSchema;
import io.swagger.v3.oas.models.media.Schema;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.measure.Quantity;
import javax.measure.Unit;

import io.swagger.v3.oas.models.media.StringSchema;
import lombok.val;
import org.springdoc.core.customizers.PropertyCustomizer;

public class UnitApiPropertyCustomizer implements PropertyCustomizer {

    @Override
    @SuppressWarnings("unchecked")
    public Schema<?> customize(Schema property, AnnotatedType type) {
        if (isApplicable(type.getType())) {
            Unit<?> unit = resolveUnit(type);
            Set<Annotation> constraints = resolveConstraints(type);
            QuantityShape shape = resolveShape(type);
            // Also clear types and add type to types set, otherwise io.swagger.v3.core.util.AnnotationsUtils.clone
            // will not work correctly, and we get a property with type=null and types=[object].
            switch (shape) {
                case OBJECT:
                    property.setType("quantity");
                    if (property.getTypes() != null) {
                        property.getTypes().clear();
                    }
                    property.addType("quantity");
                    property.setProperties(buildQuantityObjectProperties());
                    break;
                case NUMBER:
                    property.setType("number");
                    if (property.getTypes() != null) {
                        property.getTypes().clear();
                    }
                    property.addType("number");
                    property.setProperties(null);
                    break;
                case NUMERIC_STRING:
                case STRING:
                default:
                    property.setType("string");
                    if (property.getTypes() != null) {
                        property.getTypes().clear();
                    }
                    property.addType("string");
                    property.setProperties(null);
            }
            String description = buildDescription(type, property, unit, constraints);
            property.setDescription(description);
            property.setExample("1" + (unit.getSymbol() != null ? " " + unit.getSymbol() : ""));
        }
        return property;
    }


    @SuppressWarnings({"rawtypes", "unchecked"})
    private Unit<?> resolveUnit(AnnotatedType type) {
        JavaType quantityType = (JavaType) type.getType();
        Class<?> quantityBoundType = quantityType.getBindings().getBoundType(0).getRawClass();
        Unit<?> unit = UnitsApiService.getInstance().getUnit((Class) quantityBoundType);
        JsonUnit jsonUnit = resolveJsonUnit(type);
        if (jsonUnit == null) {
            return unit;
        }
        Unit<?> resolvedUnit = JsonUnitHelper.getUnitInstance(jsonUnit);
        return resolvedUnit != null ? resolvedUnit : unit;
    }

    private JsonUnit resolveJsonUnit(AnnotatedType type) {
        JsonUnit jsonUnit =
                Stream.of(type.getCtxAnnotations())
                        .filter((it) -> it instanceof JsonUnit)
                        .map((it) -> (JsonUnit) it)
                        .findFirst()
                        .orElse(null);
        return jsonUnit;
    }

    private QuantityShape resolveShape(AnnotatedType type) {
        JsonUnit jsonUnit = resolveJsonUnit(type);
        if (jsonUnit == null) return QuantityShape.NUMBER;
        return JsonUnitHelper.getShape(jsonUnit);
    }

    private Set<Annotation> resolveConstraints(AnnotatedType type) {
        return Arrays.stream(type.getCtxAnnotations())
                .filter(it -> UnitMin.class.isAssignableFrom(it.annotationType()) || UnitMax.class.isAssignableFrom(it.annotationType()))
                .collect(Collectors.toSet());
    }

    private boolean isApplicable(Type type) {
        return (type instanceof JavaType)
                && Quantity.class.isAssignableFrom(((JavaType) type).getRawClass());
    }

    private String buildDescription(AnnotatedType type, Schema property, Unit<?> unit, Set<Annotation> constraints) {
        String name = type.getPropertyName();
        String description = property.getDescription();
        String result = "";
        if (unit != null) {
            String unitName = unit.getName();
            String unitSymbol = unit.getSymbol();

            // could be localized
            result += name + " is given in " + unitName;
            if (unitSymbol != null) {
                result += " (" + unitSymbol + ")";
            }
        }
        if (constraints != null) {
            if (constraints.size() == 2) {
                // Case 1: UnitMin and UnitMax are present in the constraints set
                UnitMin min = getAnnotation(constraints, UnitMin.class);
                Unit<?> minUnit = AbstractUnitValidator.createUnit(min.unit());
                UnitMax max = getAnnotation(constraints, UnitMax.class);
                Unit<?> maxUnit = AbstractUnitValidator.createUnit(max.unit());

                result += " and must be between " + min.value() + " " + minUnit.getSymbol() + " and " + max.value() + " " + maxUnit.getSymbol();
            } else if (constraints.stream().anyMatch(it -> UnitMin.class.isAssignableFrom(it.annotationType()))) {
                // Case 2: UnitMin is present in the constraints set
                UnitMin min = getAnnotation(constraints, UnitMin.class);
                Unit<?> minUnit = AbstractUnitValidator.createUnit(min.unit());

                result += " and must be greater than " + min.value() + " " + minUnit.getSymbol();
            } else if (constraints.stream().anyMatch(it -> UnitMax.class.isAssignableFrom(it.annotationType()))) {
                // Case 2: UnitMax is present in the constraints set
                UnitMax max = getAnnotation(constraints, UnitMax.class);
                Unit<?> maxUnit = AbstractUnitValidator.createUnit(max.unit());

                result += " and must be less than " + max.value() + " " + maxUnit.getSymbol();
            }
        }
        if (description != null) {
            if (!result.isEmpty()) {
                result += "\n";
            }
            result += description;
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private <T> T getAnnotation(Collection<Annotation> events, Class<T> clazz) {
        return (T) events.stream()
                .filter(it -> clazz.isAssignableFrom(it.annotationType()))
                .findFirst()
                .orElse(null);
    }

    private Map<String, Schema<?>> buildQuantityObjectProperties() {
        Map<String, Schema<?>> properties = new HashMap<>();
        NumberSchema value = new NumberSchema();
        properties.put("value", value);

        StringSchema unit = new StringSchema();
        properties.put("unit", unit);

        return properties;
    }
}
