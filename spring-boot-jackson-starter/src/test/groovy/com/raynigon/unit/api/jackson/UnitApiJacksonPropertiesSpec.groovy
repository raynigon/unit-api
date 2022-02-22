package com.raynigon.unit.api.jackson

import com.raynigon.unit.api.jackson.config.UnitApiFeature
import spock.lang.Specification

class UnitApiJacksonPropertiesSpec extends Specification {

    def 'enabled props'() {
        given:
        def props = new UnitApiJacksonProperties()

        when:
        props.setFeatures(Map.of(UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION, true))

        then:
        props.enabledFeatures == [UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION] as Set
        props.disabledFeatures == [] as Set

    }

    def 'disabled props'() {
        given:
        def props = new UnitApiJacksonProperties()

        when:
        props.setFeatures(Map.of(UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION, false))

        then:
        props.enabledFeatures == [] as Set
        props.disabledFeatures == [UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION] as Set

    }
}
