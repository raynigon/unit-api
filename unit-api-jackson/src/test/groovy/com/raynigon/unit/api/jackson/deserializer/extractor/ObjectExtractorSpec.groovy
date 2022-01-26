package com.raynigon.unit.api.jackson.deserializer.extractor

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.DeserializerFactory
import com.raynigon.unit.api.jackson.helpers.DummyContext
import spock.lang.Specification

class ObjectExtractorSpec extends Specification {

    def 'deserialize numeric string'() {
        given:
        ObjectExtractor extractor = new ObjectExtractor()

        and:
        JsonParser parser = Mock()
        parser.nextToken() >>> [
                JsonToken.FIELD_NAME,
                JsonToken.VALUE_NUMBER_FLOAT,
                JsonToken.FIELD_NAME,
                JsonToken.VALUE_STRING,
                JsonToken.END_OBJECT
        ]
        parser.getValueAsString() >>> [
                "value",
                "unit",
                "unit",
                "KG",

        ]
        parser.getDoubleValue() >> 123.0
        DeserializationContext context = new DummyContext(Mock(DeserializerFactory))

        when:
        def result = extractor.extract(parser, context)

        then:
        result == "123.0 KG"
    }
}
