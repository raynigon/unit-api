package com.raynigon.unit_api.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.raynigon.unit_api.core.service.UnitsApiService;
import com.raynigon.unit_api.jackson.annotation.JsonUnit;
import com.raynigon.unit_api.jackson.annotation.JsonUnitHelper;
import com.raynigon.unit_api.jackson.exception.UnknownUnitException;
import org.apache.commons.lang3.StringUtils;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.io.IOException;


public class QuantityDeserializer extends JsonDeserializer<Quantity<?>> implements ContextualDeserializer {

    private Unit<?> unit;
    private boolean forceUnit = false;

    public QuantityDeserializer() {
        this(null, false);
    }

    protected QuantityDeserializer(Unit<?> unit, boolean forceUnit) {
        this.unit = unit;
        this.forceUnit = forceUnit;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        Class<Quantity> quantityType = (Class<Quantity>) property.getType().getBindings().getBoundType(0).getRawClass();
        this.unit = UnitsApiService.getInstance().getUnit(quantityType);

        JsonUnit unitWrapper = property.getAnnotation(JsonUnit.class);
        if (unitWrapper == null) return new QuantityDeserializer(unit, false);

        String unitName = JsonUnitHelper.getUnitName(unitWrapper);
        if (unitName != null) {
            this.unit = UnitsApiService.getInstance().getUnit(unitName);
        }
        if (this.unit == null) {
            throw new UnknownUnitException(ctxt.getParser(), unitName);
        }

        return new QuantityDeserializer(unit, true);
    }

    @Override
    public Quantity<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        switch (p.getCurrentTokenId()) {
            case JsonTokenId.ID_STRING:
                String strValue = p.getValueAsString();
                if (StringUtils.isNumeric(strValue)) {
                    return createQuantity(strValue);
                }
                return resolveQuantity(strValue);
            case JsonTokenId.ID_NUMBER_INT:
            case JsonTokenId.ID_NUMBER_FLOAT:
                return createQuantity(p.getDoubleValue());
            case JsonTokenId.ID_START_ARRAY:
            default:
                return null;
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Quantity<?> resolveQuantity(String value) {
        Quantity<?> result = UnitsApiService.getInstance().parseQuantity(value);
        return forceUnit ? result.to((Unit) this.unit) : result;
    }

    private Quantity<?> createQuantity(String value) {
        return UnitsApiService.getInstance().createQuantity(
                Double.parseDouble(value),
                unit
        );
    }

    private Quantity<?> createQuantity(double value) {
        return UnitsApiService.getInstance().createQuantity(
                value,
                unit
        );
    }
}
