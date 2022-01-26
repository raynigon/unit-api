package com.raynigon.unit.api.jackson.deserializer.extractor

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonTokenId
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.DeserializerFactory
import com.raynigon.unit.api.jackson.helpers.DummyContext
import spock.lang.Specification
import spock.lang.Unroll

class NumberExtractorSpec extends Specification {

    @Unroll
    def 'parse #tokenId should result in #expectedResult'() {
        given:
        NumberExtractor extractor = new NumberExtractor()

        and:
        JsonParser parser = Mock()
        DeserializationContext context = new DummyContext(Mock(DeserializerFactory))

        when:
        def result = extractor.extract(parser, context)

        then:
        result == expectedResult

        and:
        1 * parser.getCurrentTokenId() >> tokenId
        longValueCalls * parser.getLongValue() >> 123
        doubleValueCalls * parser.getDoubleValue() >> 123.0

        where:
        tokenId                     | longValueCalls | doubleValueCalls | expectedResult
        JsonTokenId.ID_NUMBER_INT   | 1              | 0                | 123
        JsonTokenId.ID_NUMBER_FLOAT | 0              | 1                | 123.0
    }
}
