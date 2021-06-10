package com.raynigon.unit_api.core.io

import spock.lang.Specification
import spock.lang.Unroll

import static com.raynigon.unit_api.core.units.si.SISystemUnitsConstants.Celsius
import static com.raynigon.unit_api.core.units.si.SISystemUnitsConstants.Metre

class DefaultQuantityReaderSpec extends Specification {

    @Unroll
    def '#input is read as #output'() {
        given:
        def reader = new DefaultQuantityReader()

        when:
        def result = reader.read(input)

        then:
        result == output

        where:
        input    | output
        "1 m"    | Metre(1)
        "10 m"   | Metre(10)
        "10.2 m" | Metre(10.2)
        "1 ℃"    | Celsius(1)
    }
}
