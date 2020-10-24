package com.raynigon.unit_api.core.function

import spock.lang.Ignore
import spock.lang.Specification

class CalculatorSpec extends Specification {

    @Ignore
    def 'list all available number systems'() {
        expect:
        List<NumberSystem> availableNumberSystems = CalculusUtils.getAvailableNumberSystems();
        availableNumberSystems != null
        availableNumberSystems.size() > 0
    }
}
