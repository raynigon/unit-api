package com.raynigon.unit.api.core.units.binary.bytes

import com.raynigon.unit.api.core.units.binary.BinarySystemUnitsConstants
import com.raynigon.unit.api.core.units.si.SISystemUnitsConstants
import spock.lang.Specification

class ByteSpec extends Specification {
    def "Bytes are 8 Bits"() {
        given:
        def bytes = BinarySystemUnitsConstants.Bytes(2)

        when:
        def bits = bytes.to(BinarySystemUnitsConstants.Bit)

        then:
        bytes.value * 8 == bits.value
    }

    def "Kilobytes and Kibibytes are different"() {
        when:
        def kilo = BinarySystemUnitsConstants.Kilobytes(2)
        def kibi = BinarySystemUnitsConstants.Kibibytes(2)

        then:
        kilo < kibi
    }

    def "data rates can be calculated"() {
        given:
        def bytes = BinarySystemUnitsConstants.Bytes(10)
        def time = SISystemUnitsConstants.Seconds(2)

        when:
        def rate = bytes.divide(time)

        then:
        rate.value == 5
        rate.getUnit().toString() == "B*s^-1"
    }
}
