package com.raynigon.unit.api.jackson.helpers

import com.fasterxml.jackson.annotation.ObjectIdGenerator
import com.fasterxml.jackson.annotation.ObjectIdResolver
import tools.jackson.databind.DeserializationConfig
import tools.jackson.databind.DeserializationContext
import tools.jackson.databind.KeyDeserializer
import tools.jackson.databind.ValueDeserializer
import tools.jackson.databind.deser.DeserializerCache
import tools.jackson.databind.deser.DeserializerFactory
import tools.jackson.databind.deser.ReadableObjectId
import tools.jackson.databind.deser.UnresolvedForwardReference
import tools.jackson.databind.introspect.Annotated
import tools.jackson.databind.json.JsonMapper

class DummyContext extends DeserializationContext {

    public DummyContext(DeserializerFactory df) {
        super(
                null,
                df,
                new DeserializerCache(),
                JsonMapper.builder().build().deserializationConfig(),
                null,
                null,
        )
    }

    @Override
    ReadableObjectId findObjectId(Object id, ObjectIdGenerator<?> generator, ObjectIdResolver resolver) {
        return null
    }

    @Override
    void checkUnresolvedObjectId() throws UnresolvedForwardReference {

    }

    @Override
    ValueDeserializer<Object> deserializerInstance(Annotated annotated, Object deserDef) {
        return null
    }

    @Override
    KeyDeserializer keyDeserializerInstance(Annotated annotated, Object deserDef) {
        return null
    }
}

