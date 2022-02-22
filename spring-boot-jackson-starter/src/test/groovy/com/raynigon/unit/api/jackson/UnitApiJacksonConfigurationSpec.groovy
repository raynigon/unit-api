package com.raynigon.unit.api.jackson

import com.raynigon.unit.api.jackson.config.UnitApiFeature
import spock.lang.Specification
import spock.lang.Unroll

class UnitApiJacksonConfigurationSpec extends Specification {

    @Unroll
    def 'module creation #title'() {

        given:
        def props = new UnitApiJacksonProperties()
        props.setFeatures(Map.of(UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION, enabled))
        def config = new UnitApiJacksonConfiguration(props)

        when:
        def module = config.unitApiJacksonModule()

        then:
        module.config.isEnabled(UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION) == expected

        where:
        title                   | enabled | expected
        "with enabled feature"  | true    | true
        "with disabled feature" | false   | false
    }
}
