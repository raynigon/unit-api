package com.raynigon.unit_api.jackson.deserializer.extractor

import com.fasterxml.jackson.annotation.ObjectIdGenerator
import com.fasterxml.jackson.annotation.ObjectIdResolver
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.KeyDeserializer
import com.fasterxml.jackson.databind.deser.DeserializerFactory
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId
import com.fasterxml.jackson.databind.introspect.Annotated
import com.raynigon.unit_api.jackson.deserializer.extractor.StringExtractor
import com.raynigon.unit_api.jackson.helpers.DummyContext
import spock.lang.Specification

class StringExtractorSpec extends Specification {

    def 'deserialize numeric string'() {
        given:
        StringExtractor extractor = new StringExtractor()

        and:
        JsonParser parser = Mock()
        DeserializationContext context = new DummyContext(Mock(DeserializerFactory))

        when:
        def result = extractor.extract(parser, context)

        then:
        result == 123

        and:
        1 * parser.getValueAsString() >> "123"
    }
}

