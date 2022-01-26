package com.raynigon.unit.api.jackson.deserializer.extractor


import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.DeserializerFactory
import com.raynigon.unit.api.jackson.helpers.DummyContext
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

