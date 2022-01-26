package com.raynigon.unit_api.jackson.deserializer

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.raynigon.unit_api.core.units.si.speed.KilometrePerHour
import com.raynigon.unit_api.core.units.si.speed.MetrePerSecond
import com.raynigon.unit_api.jackson.UnitApiModule
import com.raynigon.unit_api.jackson.annotation.JsonUnit
import com.raynigon.unit_api.jackson.config.UnitApiFeature
import spock.lang.Specification
import spock.lang.Unroll

import javax.measure.Quantity
import javax.measure.quantity.Speed

class QuantityDeserializerSpec extends Specification {

    ObjectMapper mapper

    def setup() {
        mapper = new ObjectMapper()
        mapper.registerModule(
                UnitApiModule.create()
                        .enable(UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION)
                        .build()
        )
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

    def 'speed with object deserialization'() {

        given:
        def source = mapper.writeValueAsString(["speed": ["value": 100, "unit": "m/s"]])

        when:
        def result = mapper.readValue(source, SystemSpeedEntity.class)

        then:
        noExceptionThrown()
        result.speed.unit == new MetrePerSecond()
        result.speed.value.toInteger() == 100
    }

    @Unroll
    def 'deserialization fails with wrong object data: #name'() {

        given:
        def source = mapper.writeValueAsString(input)

        when:
        mapper.readValue(source, SystemSpeedEntity.class)

        then:
        thrown(JsonMappingException)

        where:
        name | input
        "1"  | ["speed": ["value": 100, "unit": "m/s", "tmp": "tmp"]]
        "2"  | ["speed": ["value": 100, "units": "m/s"]]
        "3"  | ["speed": ["value": "100", "unit": "m/s"]]
        "4"  | ["speed": ["value": "100"]]
        "5"  | ["speed": ["unit": "m/s"]]
        "6"  | ["speed": ["value": 100, "unit": "wurzelprumpf"]]
        "7"  | ["speed": ["value": 100, "unit": [:]]]
        "8"  | ["speed": ["value": 100, "unit": 100]]
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

        @JsonUnit(unit = KilometrePerHour)
        public Quantity<Speed> speed
    }

    static class SystemSpeedEntity {

        public Quantity<Speed> speed
    }
}
