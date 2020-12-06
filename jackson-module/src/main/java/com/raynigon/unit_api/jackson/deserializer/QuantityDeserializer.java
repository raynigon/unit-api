package com.raynigon.unit_api.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.raynigon.unit_api.core.service.UnitsApiService;
import com.raynigon.unit_api.jackson.config.UnitApiConfig;
import com.raynigon.unit_api.jackson.annotation.JsonUnit;
import com.raynigon.unit_api.jackson.annotation.JsonUnitHelper;
import com.raynigon.unit_api.jackson.exception.MissingUnitException;
import com.raynigon.unit_api.jackson.exception.UnknownUnitException;

import java.io.IOException;
import java.util.Objects;
import javax.measure.Quantity;
import javax.measure.Unit;

import org.apache.commons.lang3.StringUtils;

import static com.raynigon.unit_api.jackson.config.UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION;

public class QuantityDeserializer extends JsonDeserializer<Quantity<?>>
        implements ContextualDeserializer {

    private final UnitApiConfig config;
    private Unit<?> unit;
    private boolean force;

    public QuantityDeserializer(UnitApiConfig config) {
        this(config, null, false);
    }

    public QuantityDeserializer(UnitApiConfig config, Unit<?> unit, boolean force) {
        Objects.requireNonNull(config);
        this.config = config;
        this.unit = unit;
        this.force = force;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
            throws JsonMappingException {
        Unit<?> unit = null;
        TypeBindings bindings = property.getType().getBindings();
        JavaType boundType = bindings.getBoundType(0);
        Class<Quantity> quantityType = (Class<Quantity>) boundType.getRawClass();

        JsonUnit unitWrapper = property.getAnnotation(JsonUnit.class);
        if (unitWrapper != null) {
            unit = JsonUnitHelper.getUnitInstance(unitWrapper);
            if (unit == null) {
                throw new UnknownUnitException(ctxt.getParser(), quantityType);
            }
            return new QuantityDeserializer(config, unit, true);
        }

        // Use System Unit only if the feature was activated
        if (config.isEnabled(SYSTEM_UNIT_ON_MISSING_ANNOTATION)) {
            unit = UnitsApiService.getInstance().getUnit(quantityType);
            if (unit == null) {
                throw new UnknownUnitException(ctxt.getParser(), quantityType);
            }
            return new QuantityDeserializer(config, unit, false);
        }

        return new QuantityDeserializer(config, null, false);
    }

    @Override
    public Quantity<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        switch (p.getCurrentTokenId()) {
            case JsonTokenId.ID_STRING:
                String strValue = p.getValueAsString();
                if (StringUtils.isNumeric(strValue)) {
                    return createQuantity(strValue, ctxt);
                }
                return resolveQuantity(strValue);
            case JsonTokenId.ID_NUMBER_INT:
                return createQuantity(p.getLongValue(), ctxt);
            case JsonTokenId.ID_NUMBER_FLOAT:
                return createQuantity(p.getDoubleValue(), ctxt);
            case JsonTokenId.ID_START_ARRAY:
            default:
                return (Quantity<?>) ctxt.handleUnexpectedToken(Quantity.class, p);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Quantity<?> resolveQuantity(String value) {
        Quantity<?> result = UnitsApiService.getInstance().parseQuantity(value);
        if (this.unit != null && force) {
            return result.to((Unit) this.unit);
        }
        return result;
    }

    private Quantity<?> createQuantity(String value, DeserializationContext ctxt) throws MissingUnitException {
        return createQuantity(Double.parseDouble(value), ctxt);
    }

    private Quantity<?> createQuantity(Number value, DeserializationContext ctxt) throws MissingUnitException {
        if (unit == null) {
            throw new MissingUnitException(ctxt.getParser(), "?");
        }
        return UnitsApiService.getInstance().createQuantity(value, unit);
    }
}
