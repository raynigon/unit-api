package com.raynigon.unit_api.springdoc;

import com.fasterxml.jackson.databind.type.SimpleType;
import com.raynigon.unit_api.core.service.UnitResolverService;
import com.raynigon.unit_api.jackson.annotation.JsonUnit;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.PropertyCustomizer;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.lang.reflect.Type;
import java.util.stream.Stream;

public class UnitApiPropertyCustomizer implements PropertyCustomizer {

    @Override
    public Schema<?> customize(Schema property, AnnotatedType type) {
        if (isApplicable(type.getType())) {
            Unit<?> unit = resolveUnit(type);
            property.setType("string");
            property.setProperties(null);
            property.setDescription(buildDescription(unit, property.getDescription()));
            property.setExample("1" + (unit.getSymbol() != null ? " " + unit.getSymbol() : ""));
        }
        return property;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private Unit<?> resolveUnit(AnnotatedType type) {
        SimpleType quantityType = (SimpleType) type.getType();
        Class<?> quantityBoundType = (quantityType.getBindings().getBoundType(0).getRawClass());
        Unit<?> unit = UnitResolverService.getInstance().getUnit((Class) quantityBoundType);
        JsonUnit jsonUnit = Stream.of(type.getCtxAnnotations())
                .filter((it) -> it instanceof JsonUnit)
                .map((it) -> (JsonUnit) it)
                .findFirst()
                .orElse(null);
        if (jsonUnit == null) return unit;
        Unit<?> resolvedUnit = UnitResolverService.getInstance().getUnit(jsonUnit.unit());
        return resolvedUnit != null ? resolvedUnit : unit;
    }

    private boolean isApplicable(Type type) {
        return (type instanceof SimpleType) &&
                Quantity.class.isAssignableFrom(((SimpleType) type).getRawClass());
    }

    private String buildDescription(Unit<?> unit, String description) {
        String result = "";
        if (unit != null) {
            result += unit.getName() + (unit.getSymbol() != null ? " in " + unit.getSymbol() : "");
        }
        if (description != null) {
            result += result.equals("") ? description : "\n" + description;
        }
        return result;
    }
}
