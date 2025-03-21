package com.raynigon.unit.api.core.io

import spock.lang.Specification
import spock.lang.Unroll

import static com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.Celsius
import static com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.Metre
import static com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.Number
import static com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.CubicMetre
import static com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.Kilometre

class DefaultQuantityReaderSpec extends Specification {

    @Unroll
    def '"#input" is read as #output'() {
        given:
        def reader = new DefaultQuantityReader()

        when:
        def result = reader.read(input)

        then:
        result == output

        where:
        input    | output
        "1" | Number(1)
        "123" | Number(123)
        "1 m"    | Metre(1)
        "10 m"   | Metre(10)
        "10.2 m" | Metre(10.2)
        "1 ℃"    | Celsius(1)
        "1℃"    | Celsius(1)
        "0.1e-10 m" | Metre(0.1e-10)
        "1234.5E10 m³" | CubicMetre(1.2345e13)
        ".123 m" |  Metre(0.123)
        "123m" | Metre(123)
        "12. km" | Kilometre(12)
        " " | null
        "" | null
    }
}
