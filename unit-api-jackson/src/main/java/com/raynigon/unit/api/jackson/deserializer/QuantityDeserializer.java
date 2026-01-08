package com.raynigon.unit.api.jackson.deserializer;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.*;
import tools.jackson.databind.type.TypeBindings;
import com.raynigon.unit.api.core.io.QuantityReader;
import com.raynigon.unit.api.core.service.UnitsApiService;
import com.raynigon.unit.api.jackson.annotation.JsonQuantityHelper;
import com.raynigon.unit.api.jackson.annotation.JsonQuantityReader;
import com.raynigon.unit.api.jackson.annotation.JsonUnit;
import com.raynigon.unit.api.jackson.annotation.JsonUnitHelper;
import com.raynigon.unit.api.jackson.config.UnitApiConfig;
import com.raynigon.unit.api.jackson.config.UnitApiFeature;
import com.raynigon.unit.api.jackson.deserializer.extractor.NumberExtractor;
import com.raynigon.unit.api.jackson.deserializer.extractor.ObjectExtractor;
import com.raynigon.unit.api.jackson.deserializer.extractor.StringExtractor;
import com.raynigon.unit.api.jackson.exception.InvalidUnitException;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.io.IOException;
import java.util.Objects;

public class QuantityDeserializer extends ValueDeserializer<Quantity<?>> {

    private final UnitApiConfig config;
    private final QuantityReader reader;
    private final boolean forceUnit;
    private final Unit<?> unit;

    public QuantityDeserializer(UnitApiConfig config) {
        this(config, null, false, null);
    }

    protected QuantityDeserializer(UnitApiConfig config, Unit<?> unit, boolean forceUnit, QuantityReader reader) {
        this.config = Objects.requireNonNull(config);
        this.unit = unit;
        this.forceUnit = forceUnit;
        this.reader = reader;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public ValueDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
            throws DatabindException {
        Unit<?> unit = null;
        TypeBindings bindings = property.getType().getBindings();
        JavaType boundType = bindings.getBoundType(0);
        Class<Quantity> quantityType = (Class<Quantity>) boundType.getRawClass();
        QuantityReader reader = UnitsApiService.reader();

        JsonQuantityReader readerWrapper = property.getAnnotation(JsonQuantityReader.class);
        if (readerWrapper != null) {
            reader = JsonQuantityHelper.getReaderInstance(readerWrapper);
        }

        JsonUnit unitWrapper = property.getAnnotation(JsonUnit.class);
        if (unitWrapper != null) {
            unit = JsonUnitHelper.getUnitInstance(unitWrapper);
            if (unit == null) {
                throw new InvalidUnitException(ctxt.getParser(), quantityType);
            }
            return new QuantityDeserializer(config, unit, true, reader);
        }

        // Use System Unit only if the feature was activated
        if (config.isEnabled(UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION)) {
            unit = UnitsApiService.getInstance().getUnit(quantityType);
            if (unit == null) {
                throw new InvalidUnitException(ctxt.getParser(), quantityType);
            }
            return new QuantityDeserializer(config, unit, false, reader);
        }

        return new QuantityDeserializer(config, null, false, reader);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Quantity<?> deserialize(JsonParser parser, DeserializationContext ctxt) {

        // Handle Number, String and Object Tokens
        // and convert them into either a number,
        // or a String containing a Unit
        int tokenId = parser.currentTokenId();
        Object value = null;
        if (NumberExtractor.HANDLED_TOKENS.contains(tokenId)) {
            value = NumberExtractor.extract(parser, ctxt);
        } else if (StringExtractor.HANDLED_TOKENS.contains(tokenId)) {
            value = StringExtractor.extract(parser, ctxt);
        } else if (ObjectExtractor.HANDLED_TOKENS.contains(tokenId)) {
            value = ObjectExtractor.extract(parser, ctxt);
        } else {
            return null;
        }

        // If the value is a Number,
        // create a Quantity with the resolved unit.
        // If the value is a String,
        // it has to contain a Unit and should be parsed.
        if (value instanceof Number) {
            return UnitsApiService.quantity((Number) value, this.unit);
        } else if (value instanceof String) {
            Quantity<?> result = this.reader.read((String) value);
            Objects.requireNonNull(result);
            return forceUnit ? result.to((Unit) this.unit) : result;
        } else {
            return null;
        }
    }
}
