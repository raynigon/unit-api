package com.raynigon.unit.api.jackson


import spock.lang.Specification

class UnitApiJacksonPropertiesSpec extends Specification {

    def 'enabled props'() {
        given:
        def props = new UnitApiJacksonProperties()

        when:
        props.setFeatures(Map.of(com.raynigon.unit.api.jackson.config.UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION, true))

        then:
        props.enabledFeatures == [com.raynigon.unit.api.jackson.config.UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION] as Set
        props.disabledFeatures == [] as Set

    }

    def 'disabled props'() {
        given:
        def props = new UnitApiJacksonProperties()

        when:
        props.setFeatures(Map.of(com.raynigon.unit.api.jackson.config.UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION, false))

        then:
        props.enabledFeatures == [] as Set
        props.disabledFeatures == [com.raynigon.unit.api.jackson.config.UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION] as Set

    }
}
