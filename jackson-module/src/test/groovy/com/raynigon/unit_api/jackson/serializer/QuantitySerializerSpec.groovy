package com.raynigon.unit_api.jackson.serializer

import com.fasterxml.jackson.databind.ObjectMapper
import com.raynigon.unit_api.core.annotation.QuantityShape
import com.raynigon.unit_api.core.units.si.length.Metre
import com.raynigon.unit_api.core.units.si.speed.KilometrePerHour
import com.raynigon.unit_api.core.units.si.temperature.Celsius
import com.raynigon.unit_api.jackson.UnitApiModule
import com.raynigon.unit_api.jackson.annotation.JsonUnit
import spock.lang.Specification

import javax.measure.Quantity
import javax.measure.quantity.Length
import javax.measure.quantity.Speed
import javax.measure.quantity.Temperature

import static com.raynigon.unit_api.core.service.UnitsApiService.quantity

class QuantitySerializerSpec extends Specification {

    def 'number deserialization'() {

        given:
        def mapper = new ObjectMapper()
        mapper.registerModule(new UnitApiModule())

        and:
        def source = new BasicNumberEntity()
        source.id = "1"
        source.speed = quantity(100, new KilometrePerHour())

        when:
        def result = mapper.writeValueAsString(source)

        then:
        noExceptionThrown()

        and:
        result == '{"id":"1","speed":100.0}'
    }

    def 'string deserialization'() {

        given:
        def mapper = new ObjectMapper()
        mapper.registerModule(new UnitApiModule())

        and:
        def source = new BasicStringEntity()
        source.id = "1"
        source.temperature = quantity(30, new Celsius())

        when:
        def result = mapper.writeValueAsString(source)

        then:
        noExceptionThrown()

        and:
        result == '{"id":"1","temperature":"30 ℃"}'
    }

    def 'object deserialization'() {

        given:
        def mapper = new ObjectMapper()
        mapper.registerModule(new UnitApiModule())

        and:
        def source = new BasicObjectEntity()
        source.id = "1"
        source.distance = quantity(100, new Metre())

        when:
        def result = mapper.writeValueAsString(source)

        then:
        noExceptionThrown()

        and:
        result == '{"id":"1","distance":{"value":100.0,"unit":"m"}}'
    }

    class BasicNumberEntity {

        public String id;

        @JsonUnit(unit = "km/h")
        public Quantity<Speed> speed;
    }

    class BasicStringEntity {

        public String id;

        @JsonUnit(unit = "℃", shape = QuantityShape.STRING)
        public Quantity<Temperature> temperature;
    }

    class BasicObjectEntity {

        public String id;

        @JsonUnit(unit = "m", shape = QuantityShape.OBJECT)
        public Quantity<Length> distance;
    }
}
