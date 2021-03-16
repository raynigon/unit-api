package com.raynigon.unit_api.jackson.annotation

import com.raynigon.unit_api.core.io.DefaultQuantityReader
import com.raynigon.unit_api.jackson.helpers.DummyQuantityWriter
import spock.lang.Specification

class JsonQuantityHelperSpec extends Specification {

    def 'instantiate class'() {
        expect:
        new JsonQuantityHelper() != null
    }

    def 'create quantity reader'() {
        given:
        def annotation = Mock(JsonQuantityReader)
        annotation.value() >> DefaultQuantityReader.class

        when:
        def result = JsonQuantityHelper.getReaderInstance(annotation)

        then:
        result instanceof DefaultQuantityReader
    }

    def 'create quantity writer'() {
        given:
        def annotation = Mock(JsonQuantityWriter)
        annotation.value() >> DummyQuantityWriter.class

        when:
        def result = JsonQuantityHelper.getWriterInstance(annotation)

        then:
        result instanceof DummyQuantityWriter
    }
}
