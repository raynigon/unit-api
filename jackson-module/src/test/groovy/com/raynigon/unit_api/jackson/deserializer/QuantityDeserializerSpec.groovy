package com.raynigon.unit_api.jackson.deserializer

import com.fasterxml.jackson.databind.ObjectMapper
import com.raynigon.unit_api.core.units.si.speed.KilometrePerHour
import com.raynigon.unit_api.core.units.si.speed.MetrePerSecond
import com.raynigon.unit_api.jackson.UnitApiModule
import com.raynigon.unit_api.jackson.annotation.JsonUnit
import spock.lang.Specification

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
        result.speed.unit == new KilometrePerHour()
        result.speed.value.toInteger() == 100
    }

    def 'metric speed with string deserialization'() {

        given:
        def source = mapper.writeValueAsString(["speed": "100 km/h"])

        when:
        def result = mapper.readValue(source, MetricSpeedEntity.class)

        then:
        noExceptionThrown()
        result.speed.unit == new KilometrePerHour()
        result.speed.value.toInteger() == 100
    }

    def 'metric speed with unit less string deserialization'() {

        given:
        def source = mapper.writeValueAsString(["speed": "100"])

        when:
        def result = mapper.readValue(source, MetricSpeedEntity.class)

        then:
        noExceptionThrown()
        result.speed.unit == new KilometrePerHour()
        result.speed.value.toInteger() == 100
    }

    def 'system unit speed with number deserialization'() {

        given:
        def source = mapper.writeValueAsString(["speed": 100])

        when:
        def result = mapper.readValue(source, SystemSpeedEntity.class)

        then:
        noExceptionThrown()
        result.speed.unit == new MetrePerSecond()
        result.speed.value.toInteger() == 100
    }

    def 'system unit speed with string deserialization'() {

        given:
        def source = mapper.writeValueAsString(["speed": "100 km/h"])

        when:
        def result = mapper.readValue(source, SystemSpeedEntity.class)

        then:
        noExceptionThrown()
        result.speed.unit.symbol == new KilometrePerHour().symbol
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
