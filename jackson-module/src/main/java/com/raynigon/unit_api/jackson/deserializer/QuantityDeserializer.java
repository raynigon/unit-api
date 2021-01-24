package com.raynigon.unit_api.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.raynigon.unit_api.core.service.UnitsApiService;
import com.raynigon.unit_api.jackson.annotation.JsonUnit;
import com.raynigon.unit_api.jackson.annotation.JsonUnitHelper;
import com.raynigon.unit_api.jackson.config.UnitApiConfig;
import com.raynigon.unit_api.jackson.exception.UnknownUnitException;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.io.IOException;
import java.util.Objects;

import static com.raynigon.unit_api.jackson.config.UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION;

public class QuantityDeserializer extends JsonDeserializer<Quantity<?>>
        implements ContextualDeserializer {

    private final QuantitySubDeserializer[] subDeserializers;
    private final UnitApiConfig config;

    public QuantityDeserializer(UnitApiConfig config) {
        this(config, null, false);
    }

    protected QuantityDeserializer(UnitApiConfig config, Unit<?> unit, boolean forceUnit) {
        this.config = Objects.requireNonNull(config);
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
        Quantity<?> result = null;
        for (QuantitySubDeserializer deserializer : subDeserializers) {
            if (!deserializer.canDeserialize(p, ctxt)) continue;
            result = deserializer.deserialize(p, ctxt);
            break;
        }
        return result;
    }
}
