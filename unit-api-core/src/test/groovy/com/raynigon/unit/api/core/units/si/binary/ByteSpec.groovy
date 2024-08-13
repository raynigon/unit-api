package com.raynigon.unit.api.core.units.si.binary

import com.raynigon.unit.api.core.units.si.SISystemUnitsConstants
import spock.lang.Specification

class ByteSpec extends Specification {
    def "Bytes are 8 Bits"() {
        given:
        def bytes = SISystemUnitsConstants.Bytes(2)

        when:
        def bits = bytes.to(SISystemUnitsConstants.Bit)

        then:
        bytes.value * 8 == bits.value
    }

    def "Kilobytes and Kibibytes are different"() {
        when:
        def kilo = SISystemUnitsConstants.Kilobytes(2)
        def kibi = SISystemUnitsConstants.Kibibytes(2)

        then:
        kilo < kibi
    }
}
