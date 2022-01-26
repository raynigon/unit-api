package com.raynigon.unit.api.jackson.exception

import com.raynigon.unit.api.core.units.si.length.Metre
import spock.lang.Specification
import spock.lang.Unroll

import javax.measure.Quantity

class UnitExceptionSpec extends Specification {

    @Unroll
    def 'MissingUnitException - constructor - #input'() {
        when:
        String message
        if (input instanceof String) {
            message = new MissingUnitException(Mock(Closeable), input).getMessage()
        } else {
            message = new MissingUnitException(Mock(Closeable), input as Class<Quantity>).getMessage()
        }

        then:
        message == expectation

        where:
        input       | expectation
        "test-unit" | "Missing Unit: test-unit"
        Metre       | "Missing Unit for Quantity Type: class com.raynigon.unit.api.core.units.si.length.Metre"
    }

    @Unroll
    def 'UnknownUnitException - constructor - #input'() {
        when:
        String message
        if (input instanceof String) {
            message = new UnknownUnitException(Mock(Closeable), input).getMessage()
        } else {
            message = new UnknownUnitException(Mock(Closeable), input as Class<Quantity>).getMessage()
        }

        then:
        message == expectation

        where:
        input       | expectation
        "test-unit" | "Unknown Unit: test-unit"
        Metre       | "Unknown Unit for Quantity Type: class com.raynigon.unit.api.core.units.si.length.Metre"
    }
}
