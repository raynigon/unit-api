package com.raynigon.unit_api.jackson.helpers

import com.fasterxml.jackson.annotation.ObjectIdGenerator
import com.fasterxml.jackson.annotation.ObjectIdResolver
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.KeyDeserializer
import com.fasterxml.jackson.databind.deser.DeserializerFactory
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId
import com.fasterxml.jackson.databind.introspect.Annotated

class DummyContext extends DeserializationContext {

    protected DummyContext(DeserializerFactory df) {
        super(df)
    }

    @Override
    ReadableObjectId findObjectId(Object id, ObjectIdGenerator<?> generator, ObjectIdResolver resolver) {
        return null
    }

    @Override
    void checkUnresolvedObjectId() throws UnresolvedForwardReference {

    }

    @Override
    JsonDeserializer<Object> deserializerInstance(Annotated annotated, Object deserDef) throws JsonMappingException {
        return null
    }

    @Override
    KeyDeserializer keyDeserializerInstance(Annotated annotated, Object deserDef) throws JsonMappingException {
        return null
    }
}

