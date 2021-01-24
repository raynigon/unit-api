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

import java.io.IOException;
import javax.measure.Quantity;
import javax.measure.Unit;

import org.apache.commons.lang3.StringUtils;

public class QuantityDeserializer extends JsonDeserializer<Quantity<?>>
        implements ContextualDeserializer {

    private final QuantitySubDeserializer[] subDeserializers;

    public QuantityDeserializer() {
        this(null, false);
    }

    protected QuantityDeserializer(Unit<?> unit, boolean forceUnit) {
        this.subDeserializers = new QuantitySubDeserializer[]{
                new QuantityNumberDeserializer(unit),
                new QuantityStringDeserializer(unit, forceUnit),
                new QuantityObjectDeserializer(unit, forceUnit)
        };
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
            throws JsonMappingException {
        Class<Quantity> quantityType =
                (Class<Quantity>) property.getType().getBindings().getBoundType(0).getRawClass();
        Unit<?> unit = UnitsApiService.getInstance().getUnit(quantityType);

        JsonUnit unitWrapper = property.getAnnotation(JsonUnit.class);
        if (unitWrapper == null) return new QuantityDeserializer(unit, false);

        String unitName = JsonUnitHelper.getUnitName(unitWrapper);
        if (unitName != null) {
            unit = UnitsApiService.getInstance().getUnit(unitName);
        }
        if (unit == null) {
            throw new UnknownUnitException(ctxt.getParser(), unitName);
        }

        return new QuantityDeserializer(unit, true);
    }

    @Override
    public Quantity<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        for (QuantitySubDeserializer deserializer : subDeserializers) {
            if (!deserializer.canDeserialize(p, ctxt)) continue;
            return deserializer.deserialize(p, ctxt);
        }
        return null;
    }
}
