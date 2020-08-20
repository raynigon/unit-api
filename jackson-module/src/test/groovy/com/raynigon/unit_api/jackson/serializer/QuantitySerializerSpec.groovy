package com.raynigon.unit_api.jackson.serializer

import com.fasterxml.jackson.databind.ObjectMapper
import com.raynigon.unit_api.jackson.UnitApiModule
import com.raynigon.unit_api.jackson.helpers.BasicEntity
import spock.lang.Specification
import tech.units.indriya.quantity.Quantities
import tech.units.indriya.unit.Units

class QuantitySerializerSpec extends Specification {

    def 'number deserialization'() {

        given:
        def mapper = new ObjectMapper()
        mapper.registerModule(new UnitApiModule())

        and:
        def source = new BasicEntity()
        source.id = "1"
        source.speed = Quantities.getQuantity(100, Units.KILOMETRE_PER_HOUR)
        source.temperature = Quantities.getQuantity(30, Units.CELSIUS)

        when:
        def result = mapper.writeValueAsString(source)

        then:
        noExceptionThrown()

        and:
        result == '{"id":"1","speed":100.0,"temperature":"30 ℃","distance":null}'
    }


}
