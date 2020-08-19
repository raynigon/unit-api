package com.github.raynigon.unit_api_starter.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.raynigon.unit_api_starter.jackson.helpers.BasicEntity
import spock.lang.Specification

class UnitApiJacksonModuleSpec extends Specification {

    def 'registration works'() {

        given:
        def mapper = new ObjectMapper()

        when:
        mapper.registerModule(new UnitApiModule())

        then:
        mapper.getRegisteredModuleIds() == ([UnitApiModule.class.getName()] as Set)
    }

    def 'basic deserialization'() {

        given:
        def mapper = new ObjectMapper()
        mapper.registerModule(new UnitApiModule())

        and:
        def source = mapper.writeValueAsString([
                "id"         : "1",
                "speed"      : 100,
                "temperature": 30
        ])

        when:
        def result = mapper.readValue(source, BasicEntity.class)

        then:
        noExceptionThrown()

        and:
        result.id == "1"
    }
}
