package com.raynigon.unit_api.jackson.deserializer

import com.fasterxml.jackson.databind.ObjectMapper
import com.raynigon.unit_api.jackson.UnitApiModule
import com.raynigon.unit_api.jackson.annotation.JsonUnit
import com.raynigon.unit_api.jackson.helpers.BasicEntity
import spock.lang.Ignore
import spock.lang.Specification
import tech.units.indriya.unit.Units

import javax.measure.Quantity
import javax.measure.quantity.Speed

class QuantityDeserializerSpec extends Specification {

    ObjectMapper mapper

    def setup() {
        mapper = new ObjectMapper()
        mapper.registerModule(new UnitApiModule())
    }

    def 'metric speed with number deserialization'() {

        given:
        def source = mapper.writeValueAsString(["speed": 100])

        when:
        def result = mapper.readValue(source, MetricSpeedEntity.class)

        then:
        noExceptionThrown()
        result.speed.unit == Units.KILOMETRE_PER_HOUR
        result.speed.value.toInteger() == 100
    }

    def 'metric speed with string deserialization'() {

        given:
        def source = mapper.writeValueAsString(["speed": "100 km/h"])

        when:
        def result = mapper.readValue(source, MetricSpeedEntity.class)

        then:
        noExceptionThrown()
        result.speed.unit == Units.KILOMETRE_PER_HOUR
        result.speed.value.toInteger() == 100
    }

    def 'metric speed with unit less string deserialization'() {

        given:
        def source = mapper.writeValueAsString(["speed": "100"])

        when:
        def result = mapper.readValue(source, MetricSpeedEntity.class)

        then:
        noExceptionThrown()
        result.speed.unit == Units.KILOMETRE_PER_HOUR
        result.speed.value.toInteger() == 100
    }

    def 'system unit speed with number deserialization'() {

        given:
        def source = mapper.writeValueAsString(["speed": 100])

        when:
        def result = mapper.readValue(source, SystemSpeedEntity.class)

        then:
        noExceptionThrown()
        result.speed.unit == Units.METRE_PER_SECOND
        result.speed.value.toInteger() == 100
    }

    def 'system unit speed with string deserialization'() {

        given:
        def source = mapper.writeValueAsString(["speed": "100 km/h"])

        when:
        def result = mapper.readValue(source, SystemSpeedEntity.class)

        then:
        noExceptionThrown()
        result.speed.unit.symbol == Units.KILOMETRE_PER_HOUR.symbol
        result.speed.value.toInteger() == 100
    }

    static class MetricSpeedEntity {

        @JsonUnit(unit = "km/h")
        public Quantity<Speed> speed
    }

    static class SystemSpeedEntity {

        public Quantity<Speed> speed
    }
}
