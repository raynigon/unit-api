package com.raynigon.unit_api.jackson.deserializer

import com.fasterxml.jackson.databind.ObjectMapper
import com.raynigon.unit_api.jackson.UnitApiModule
import com.raynigon.unit_api.jackson.helpers.BasicEntity
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
                "temperature": 30.0,
                "distance"   : 10
        ])

        when:
        def result = mapper.readValue(source, BasicEntity.class)

        then:
        noExceptionThrown()

        and:
        result.id == "1"
        result.speed.to(Units.KILOMETRE_PER_HOUR).value.toInteger() == 100
        result.temperature.to(Units.CELSIUS).value.toInteger() == 30
        result.distance.to(Units.METRE).value.toInteger() == 10_000
    }

    def 'string deserialization'() {

        given:
        def mapper = new ObjectMapper()
        mapper.registerModule(new UnitApiModule())

        and:
        def source = mapper.writeValueAsString([
                "id"         : "1",
                "speed"      : "100 km/h",
                "temperature": "30 ℃",
                "distance"   : "10 km"
        ])

        when:
        def result = mapper.readValue(source, BasicEntity.class)

        then:
        noExceptionThrown()

        and:
        result.id == "1"
        result.speed.to(Units.KILOMETRE_PER_HOUR).value.toInteger() == 100
        result.temperature.to(Units.CELSIUS).value.toInteger() == 30
        result.distance.to(Units.METRE).value.toInteger() == 10_000
    }
}
