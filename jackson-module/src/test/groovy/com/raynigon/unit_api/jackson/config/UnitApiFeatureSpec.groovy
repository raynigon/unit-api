package com.raynigon.unit_api.jackson.config

import spock.lang.Specification

class UnitApiFeatureSpec extends Specification {

    def 'check if enabled by default'() {
        expect:
        !UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION.enabledByDefault()
    }
}
