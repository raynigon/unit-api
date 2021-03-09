package com.raynigon.unit_api.springdoc;

import com.fasterxml.jackson.databind.type.SimpleType;
import com.raynigon.unit_api.core.service.UnitsApiService;
import com.raynigon.unit_api.jackson.annotation.JsonUnit;
import com.raynigon.unit_api.jackson.annotation.JsonUnitHelper;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.models.media.Schema;
import java.lang.reflect.Type;
import java.util.stream.Stream;
import javax.measure.Quantity;
import javax.measure.Unit;
import org.springdoc.core.customizers.PropertyCustomizer;

public class UnitApiPropertyCustomizer implements PropertyCustomizer {

  @Override
  public Schema<?> customize(Schema property, AnnotatedType type) {
    if (isApplicable(type.getType())) {
      Unit<?> unit = resolveUnit(type);
      property.setType("string"); // could be configurable
      property.setProperties(null);
      property.setDescription(
          buildDescription(property.getName(), unit, property.getDescription()));
      property.setExample("1" + (unit.getSymbol() != null ? " " + unit.getSymbol() : ""));
    }
    return property;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private Unit<?> resolveUnit(AnnotatedType type) {
    SimpleType quantityType = (SimpleType) type.getType();
    Class<?> quantityBoundType = (quantityType.getBindings().getBoundType(0).getRawClass());
    Unit<?> unit = UnitsApiService.getInstance().getUnit((Class) quantityBoundType);
    JsonUnit jsonUnit =
        Stream.of(type.getCtxAnnotations())
            .filter((it) -> it instanceof JsonUnit)
            .map((it) -> (JsonUnit) it)
            .findFirst()
            .orElse(null);
    if (jsonUnit == null) return unit;
    Unit<?> resolvedUnit = JsonUnitHelper.getUnitInstance(jsonUnit);
    return resolvedUnit != null ? resolvedUnit : unit;
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
}
