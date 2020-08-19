package com.github.raynigon.unit_api_starter.jackson.deserializers

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.raynigon.unit_api_starter.jackson.UnitApiModule
import com.github.raynigon.unit_api_starter.jackson.helpers.BasicEntity
import spock.lang.Specification
import tech.units.indriya.unit.Units

class QuantityDeserializerSpec extends Specification {

    def 'number deserialization'() {

        given:
        def mapper = new ObjectMapper()
        mapper.registerModule(new UnitApiModule())

        and:
        def source = mapper.writeValueAsString([
                "id"         : "1",
                "speed"      : 100,
                "temperature": 30.0
        ])

        when:
        def result = mapper.readValue(source, BasicEntity.class)

        then:
        noExceptionThrown()

        and:
        result.id == "1"
        result.speed.to(Units.KILOMETRE_PER_HOUR).value.toInteger() == 100
        result.temperature.to(Units.CELSIUS).value.toInteger() == 30
    }

    def 'string deserialization'() {

        given:
        def mapper = new ObjectMapper()
        mapper.registerModule(new UnitApiModule())

        and:
        def source = mapper.writeValueAsString([
                "id"         : "1",
                "speed"      : "100 km/h",
                "temperature": "30 ℃"
        ])

        when:
        def result = mapper.readValue(source, BasicEntity.class)

        then:
        noExceptionThrown()

        and:
        result.id == "1"
        result.speed.to(Units.KILOMETRE_PER_HOUR).value.toInteger() == 100
        result.temperature.to(Units.CELSIUS).value.toInteger() == 30
    }
}
