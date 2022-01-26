package com.raynigon.unit_api.jackson.deserializer.extractor

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.core.JsonTokenId
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.DeserializerFactory
import com.raynigon.unit_api.jackson.helpers.DummyContext
import spock.lang.Specification

import static com.fasterxml.jackson.core.JsonTokenId.ID_FIELD_NAME
import static com.fasterxml.jackson.core.JsonTokenId.ID_STRING
import static com.fasterxml.jackson.core.JsonTokenId.ID_NUMBER_FLOAT

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
