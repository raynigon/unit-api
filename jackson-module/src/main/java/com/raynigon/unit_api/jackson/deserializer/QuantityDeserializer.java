package com.raynigon.unit_api.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.raynigon.unit_api.core.service.UnitResolverService;
import com.raynigon.unit_api.jackson.annotation.JsonUnit;
import tech.units.indriya.quantity.Quantities;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.io.IOException;

public class QuantityDeserializer extends JsonDeserializer<Quantity<?>> implements ContextualDeserializer {

    private Unit<?> unit;
    private boolean forceUnit = false;

    public QuantityDeserializer(){}

    protected QuantityDeserializer(Unit<?> unit, boolean forceUnit){
        this.unit = unit;
        this.forceUnit = forceUnit;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        Class<Quantity> quantityType = (Class<Quantity>) property.getType().getBindings().getBoundType(0).getRawClass();
        this.unit = UnitResolverService.getInstance().getUnit(quantityType);

        JsonUnit unitWrapper = property.getAnnotation(JsonUnit.class);
        if (unitWrapper==null) return new QuantityDeserializer(unit, false);

        String unitName = unitWrapper.unit();
        if ("".equalsIgnoreCase(unitName)) return new QuantityDeserializer(unit, false);
        this.unit = UnitResolverService.getInstance().getUnit(unitName);
        if (this.unit == null) throw new RuntimeException("Unknown Unit: "+unitName);

        return new QuantityDeserializer(unit, true);
    }

    @Override
    public Quantity<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        switch (p.getCurrentTokenId()) {
            case JsonTokenId.ID_STRING:
                Quantity<?> result = Quantities.getQuantity(p.getValueAsString());
                //noinspection unchecked,rawtypes
                return forceUnit ? result.to((Unit) this.unit) : result;
            case JsonTokenId.ID_NUMBER_INT:
            case JsonTokenId.ID_NUMBER_FLOAT:
                return Quantities.getQuantity(
                        p.getValueAsDouble(),
                        unit
                );
            case JsonTokenId.ID_START_ARRAY:
            default:
                return null;
        }
    }
}