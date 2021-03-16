package com.raynigon.unit_api.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.raynigon.unit_api.core.io.DefaultQuantityReader;
import com.raynigon.unit_api.core.io.QuantityReader;
import com.raynigon.unit_api.core.service.UnitsApiService;
import com.raynigon.unit_api.jackson.annotation.JsonQuantityHelper;
import com.raynigon.unit_api.jackson.annotation.JsonQuantityReader;
import com.raynigon.unit_api.jackson.annotation.JsonUnit;
import com.raynigon.unit_api.jackson.annotation.JsonUnitHelper;
import com.raynigon.unit_api.jackson.config.UnitApiConfig;
import com.raynigon.unit_api.jackson.deserializer.extractor.NumberExtractor;
import com.raynigon.unit_api.jackson.deserializer.extractor.ObjectExtractor;
import com.raynigon.unit_api.jackson.deserializer.extractor.StringExtractor;
import com.raynigon.unit_api.jackson.exception.UnknownUnitException;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.io.IOException;
import java.util.Objects;

import static com.raynigon.unit_api.jackson.config.UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION;

public class QuantityDeserializer extends JsonDeserializer<Quantity<?>>
        implements ContextualDeserializer {

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
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
            throws JsonMappingException {
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
                throw new UnknownUnitException(ctxt.getParser(), quantityType);
            }
            return new QuantityDeserializer(config, unit, true, reader);
        }

        // Use System Unit only if the feature was activated
        if (config.isEnabled(SYSTEM_UNIT_ON_MISSING_ANNOTATION)) {
            unit = UnitsApiService.getInstance().getUnit(quantityType);
            if (unit == null) {
                throw new UnknownUnitException(ctxt.getParser(), quantityType);
            }
            return new QuantityDeserializer(config, unit, false, reader);
        }

        return new QuantityDeserializer(config, null, false, reader);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Quantity<?> deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {

        // Handle Number, String and Object Tokens
        // and convert them into either a number,
        // or a String containing a Unit
        int tokenId = parser.getCurrentTokenId();
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
