package com.raynigon.unit_api.springdoc;

import com.fasterxml.jackson.databind.type.SimpleType;
import com.raynigon.unit_api.core.service.UnitResolverService;
import com.raynigon.unit_api.jackson.annotation.JsonUnit;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.PropertyCustomizer;
import tech.units.indriya.unit.Units;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.lang.reflect.Type;
import java.util.stream.Stream;

public class UnitApiPropertyCustomizer implements PropertyCustomizer {

    @Override
    public Schema<?> customize(Schema property, AnnotatedType type) {
        if (isApplicable(type.getType())) {
            Unit<?> unit = resolveUnit(type);
            property.setType("number | string"); // could be configurable
            property.setProperties(null);
            property.setDescription(buildDescription(property.getName(), unit, property.getDescription()));
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

    private String buildDescription(String name, Unit<?> unit, String description) {
        String result = "";
        if (unit != null) {
            String unitName = getUnitName(unit);
            String unitSymbol = getUnitSymbol(unit);

            // could be localized
            result += name + " is given in " + unitName;
            if (unitSymbol != null) {
                result += " (" + unitSymbol + ")";
            }
        }
        if (description != null) {
            if (!result.equals("")) {
                result += "\n";
            }
            result += description;
        }
        return result;
    }

    /**
     *  Temporary Solution until we figure out something better
     */
    private String getUnitSymbol(Unit<?> unit) {
        if (unit.getSymbol() != null) return unit.getSymbol();
        if (unit == Units.METRE_PER_SECOND) return "m/s";
        if (unit == Units.KILOMETRE_PER_HOUR) return "km/h";
        return null;
    }

    /**
     *  Temporary Solution until we figure out something better
     */
    private String getUnitName(Unit<?> unit) {
        if (unit.getName() != null) return unit.getName();
        if (unit == Units.METRE_PER_SECOND) return "metre per second";
        if (unit == Units.KILOMETRE_PER_HOUR) return "kilometre per hour";
        return "unknown";
    }
}
