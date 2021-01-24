package com.raynigon.unit_api.jackson.deserializer

import com.raynigon.unit_api.jackson.exception.MissingUnitException
import spock.lang.Specification

class QuantityNumberDeserializerSpec extends Specification {

    def 'unit equals null'() {
        given:
        QuantityNumberDeserializer deserializer = new QuantityNumberDeserializer(null)

        when:
        deserializer.deserialize(null, null)

        then:
        thrown(MissingUnitException)
    }
}
