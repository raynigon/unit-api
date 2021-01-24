package com.raynigon.unit_api.jackson.exception

import com.raynigon.unit_api.core.units.si.length.Metre
import spock.lang.Specification
import spock.lang.Unroll

class UnitExceptionSpec extends Specification {

    @Unroll
    def 'MissingUnitException - constructor - #input'() {
        expect:
        new MissingUnitException(Mock(Closeable), input).getMessage() == expectation

        where:
        input       | expectation
        "test-unit" | "Missing Unit: test-unit"
        Metre       | "Missing Unit for Quantity Type: class com.raynigon.unit_api.core.units.si.length.Metre"
    }

    @Unroll
    def 'UnknownUnitException - constructor - #input'() {
        expect:
        new UnknownUnitException(Mock(Closeable), input).getMessage() == expectation

        where:
        input       | expectation
        "test-unit" | "Unknown Unit: test-unit"
        Metre       | "Unknown Unit for Quantity Type: class com.raynigon.unit_api.core.units.si.length.Metre"
    }
}
