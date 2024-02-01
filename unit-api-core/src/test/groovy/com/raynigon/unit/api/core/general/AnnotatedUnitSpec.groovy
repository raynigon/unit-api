package com.raynigon.unit.api.core.general

import spock.lang.Ignore
import spock.lang.Specification

class AnnotatedUnitSpec extends Specification {

    @Ignore
    def 'test'() {
        expect:
        false
    }
}
