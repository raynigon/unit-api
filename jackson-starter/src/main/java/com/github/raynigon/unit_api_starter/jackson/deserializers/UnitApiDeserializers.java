package com.github.raynigon.unit_api_starter.jackson.deserializers;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.ReferenceType;

import javax.measure.Quantity;

public class UnitApiDeserializers extends Deserializers.Base {

    @Override
    public JsonDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        if (type.hasRawClass(Quantity.class)) {
            return new QuantityDeserializer(type);
        }
        return null;
    }
}
