package com.raynigon.unit_api.jackson.deserializer

import com.raynigon.unit_api.jackson.exception.MissingUnitException
import spock.lang.Specification

class JacksonNumberDeserializerSpec extends Specification {

    def 'unit equals null'() {
        given:
        JacksonNumberDeserializer deserializer = new JacksonNumberDeserializer(null)

        when:
        deserializer.deserialize(null, null)

        then:
        thrown(MissingUnitException)
    }
}
