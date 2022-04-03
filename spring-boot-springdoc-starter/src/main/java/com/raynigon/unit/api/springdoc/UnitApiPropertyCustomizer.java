package com.raynigon.unit.api.springdoc;

import com.fasterxml.jackson.databind.type.SimpleType;
import com.raynigon.unit.api.core.annotation.QuantityShape;
import com.raynigon.unit.api.core.service.UnitsApiService;
import com.raynigon.unit.api.jackson.annotation.JsonUnit;
import com.raynigon.unit.api.jackson.annotation.JsonUnitHelper;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.models.media.NumberSchema;
import io.swagger.v3.oas.models.media.Schema;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import javax.measure.Quantity;
import javax.measure.Unit;

import io.swagger.v3.oas.models.media.StringSchema;
import org.springdoc.core.customizers.PropertyCustomizer;

public class UnitApiPropertyCustomizer implements PropertyCustomizer {

    @Override
    @SuppressWarnings("unchecked")
    public Schema<?> customize(Schema property, AnnotatedType type) {
        if (isApplicable(type.getType())) {
            Unit<?> unit = resolveUnit(type);
            QuantityShape shape = resolveShape(type);
            switch (shape) {
                case OBJECT:
                    property.setType("quantity");
                    property.setProperties(buildQuantityObjectProperties());
                    break;
                case NUMBER:
                    property.setType("number");
                    property.setProperties(null);
                    break;
                case NUMERIC_STRING:
                case STRING:
                default:
                    property.setType("string");
                    property.setProperties(null);
            }
            property.setDescription(
                    buildDescription(type.getPropertyName(), unit, property.getDescription()));
            property.setExample("1" + (unit.getSymbol() != null ? " " + unit.getSymbol() : ""));
        }
        return property;
    }

    private QuantityShape resolveShape(AnnotatedType type) {
        JsonUnit jsonUnit = resolveJsonUnit(type);
        if (jsonUnit == null) return QuantityShape.NUMBER;
        return JsonUnitHelper.getShape(jsonUnit);
    }


    @SuppressWarnings({"rawtypes", "unchecked"})
    private Unit<?> resolveUnit(AnnotatedType type) {
        SimpleType quantityType = (SimpleType) type.getType();
        Class<?> quantityBoundType = (quantityType.getBindings().getBoundType(0).getRawClass());
        Unit<?> unit = UnitsApiService.getInstance().getUnit((Class) quantityBoundType);
        JsonUnit jsonUnit = resolveJsonUnit(type);
        if (jsonUnit == null) return unit;
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

    private boolean isApplicable(Type type) {
        return (type instanceof SimpleType)
                && Quantity.class.isAssignableFrom(((SimpleType) type).getRawClass());
    }

    private String buildDescription(String name, Unit<?> unit, String description) {
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
        if (description != null) {
            if (!result.equals("")) {
                result += "\n";
            }
            result += description;
        }
        return result;
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
