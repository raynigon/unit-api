package com.raynigon.unit.api.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.raynigon.unit.api.jackson.helpers.BasicEntity
import spock.lang.Specification

import static com.raynigon.unit.api.jackson.config.UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION
import static com.raynigon.unit.api.jackson.helpers.BasicEntity.*

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
        // init_unit_api_jackson_basic {
        ObjectMapper mapper = new ObjectMapper()
        mapper.registerModule(new UnitApiModule())
        // }

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

    def 'basic builder works'() {

        when:
        UnitApiModule module = UnitApiModule.create().enable(SYSTEM_UNIT_ON_MISSING_ANNOTATION).build()

        then:
        module.getConfig().isEnabled(SYSTEM_UNIT_ON_MISSING_ANNOTATION)
    }

    def 'withFeatures builder works'() {

        when:
        // init_unit_api_jackson_with_features {
        UnitApiModule module = UnitApiModule.withFeatures(SYSTEM_UNIT_ON_MISSING_ANNOTATION)
        // }

        then:
        module.getConfig().isEnabled(SYSTEM_UNIT_ON_MISSING_ANNOTATION)
    }

    def 'withoutFeatures builder works'() {

        when:
        def module = UnitApiModule.withoutFeatures(SYSTEM_UNIT_ON_MISSING_ANNOTATION)

        then:
        !module.getConfig().isEnabled(SYSTEM_UNIT_ON_MISSING_ANNOTATION)
    }
}
