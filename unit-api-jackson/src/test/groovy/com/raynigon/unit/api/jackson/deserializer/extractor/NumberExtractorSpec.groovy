package com.raynigon.unit.api.jackson.deserializer.extractor

import tools.jackson.core.JsonParser
import tools.jackson.core.JsonTokenId
import tools.jackson.databind.DeserializationConfig
import tools.jackson.databind.DeserializationContext
import tools.jackson.databind.deser.DeserializerFactory
import com.raynigon.unit.api.jackson.helpers.DummyContext
import spock.lang.Specification
import spock.lang.Unroll
import tools.jackson.databind.json.JsonMapper

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
        1 * parser.currentTokenId() >> tokenId
        longValueCalls * parser.getLongValue() >> 123
        doubleValueCalls * parser.getDoubleValue() >> 123.0

        where:
        tokenId                     | longValueCalls | doubleValueCalls | expectedResult
        JsonTokenId.ID_NUMBER_INT   | 1              | 0                | 123
        JsonTokenId.ID_NUMBER_FLOAT | 0              | 1                | 123.0
    }
}
