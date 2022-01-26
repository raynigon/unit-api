package com.raynigon.unit.api.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class UnitApiModuleSpec extends Specification {

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
        def result = mapper.readValue(source, com.raynigon.unit.api.jackson.helpers.BasicEntity.class)

        then:
        noExceptionThrown()

        and:
        result.id == "1"
    }

    def 'basic builder works'() {

        when:
        def module = UnitApiModule.create().enable(com.raynigon.unit.api.jackson.config.UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION).build()

        then:
        module.getConfig().isEnabled(com.raynigon.unit.api.jackson.config.UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION)
    }

    def 'withFeatures builder works'() {

        when:
        def module = UnitApiModule.withFeatures(com.raynigon.unit.api.jackson.config.UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION)

        then:
        module.getConfig().isEnabled(com.raynigon.unit.api.jackson.config.UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION)
    }

    def 'withoutFeatures builder works'() {

        when:
        def module = UnitApiModule.withoutFeatures(com.raynigon.unit.api.jackson.config.UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION)

        then:
        !module.getConfig().isEnabled(com.raynigon.unit.api.jackson.config.UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION)
    }
}
