package com.raynigon.unit_api.core.deserializer


import spock.lang.Specification
import spock.lang.Unroll

import static com.raynigon.unit_api.core.units.si.SISystemUnitsConstants.Metre
import static com.raynigon.unit_api.core.units.si.SISystemUnitsConstants.MetrePerSquaredSecond

class DefaultQuantityStringDeserializerSpec extends Specification {

    private DefaultQuantityStringDeserializer deserializer = new DefaultQuantityStringDeserializer()

    @Unroll
    def 'parse #input'() {

        when:
        def result = deserializer.deserialize(input)

        then:
        result == expected

        where:
        input      | expected
        null       | null
        "1 m"      | Metre(1)
        "1.0 m"    | Metre(1)
        "1.2 m"    | Metre(1.2)
        "1.2 m/s²" | MetrePerSquaredSecond(1.2)
        "1m"       | Metre(1)
        "1.0m"     | Metre(1)
        "1.2m"     | Metre(1.2)
        "1.2m/s²"  | MetrePerSquaredSecond(1.2)
        "-1.0 m"   | Metre(-1)
        "-1.2 m"   | Metre(-1.2)
        "-1m"      | Metre(-1)
        "-1.2m"    | Metre(-1.2)
    }
}
