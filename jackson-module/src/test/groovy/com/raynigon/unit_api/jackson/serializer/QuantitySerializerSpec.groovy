package com.raynigon.unit_api.jackson.serializer

import com.fasterxml.jackson.databind.ObjectMapper
import com.raynigon.unit_api.core.service.UnitsApiService
import com.raynigon.unit_api.core.units.si.speed.KilometrePerHour
import com.raynigon.unit_api.core.units.si.temperature.Celsius
import com.raynigon.unit_api.jackson.UnitApiModule
import com.raynigon.unit_api.jackson.helpers.BasicEntity
import spock.lang.Specification

import static com.raynigon.unit_api.core.service.UnitsApiService.quantity

class QuantitySerializerSpec extends Specification {

    def 'number deserialization'() {

        given:
        def mapper = new ObjectMapper()
        mapper.registerModule(new UnitApiModule())

        and:
        def source = new BasicEntity()
        source.id = "1"
        source.speed = quantity(100, new KilometrePerHour())
        source.temperature = quantity(30, new Celsius())

        when:
        def result = mapper.writeValueAsString(source)

        then:
        noExceptionThrown()

        and:
        result == '{"id":"1","speed":100.0,"temperature":"30 ℃","distance":null}'
    }


}
