package com.raynigon.unit.api.core.units.si


import spock.lang.Specification
import spock.lang.Unroll

import static com.raynigon.unit.api.core.service.UnitsApiService.quantity


class SISystemSpec extends Specification {

    @Unroll
    def 'registered unit: #symbol'() {
        given:
        def system = new SISystem()

        when:
        def actualUnit = system.getUnit(symbol)

        then:
        expectedUnit == actualUnit

        where:
        symbol   | expectedUnit
        // acceleration
        "m/s²"   | new com.raynigon.unit.api.core.units.si.acceleration.MetrePerSquaredSecond()
        // energy
        "J"      | new com.raynigon.unit.api.core.units.si.energy.Joule()
        "Wh"     | new com.raynigon.unit.api.core.units.si.energy.WattHour()
        "kWh"    | new com.raynigon.unit.api.core.units.si.energy.KiloWattHour()
        // force
        "N"      | new com.raynigon.unit.api.core.units.si.force.Newton()
        // length
        "m"      | new com.raynigon.unit.api.core.units.si.length.Metre()
        "km"     | new com.raynigon.unit.api.core.units.si.length.Kilometre()
        "mm"     | new com.raynigon.unit.api.core.units.si.length.Millimetre()
        // mass
        "kg"     | new com.raynigon.unit.api.core.units.si.mass.Kilogram()
        // power
        "W"      | new com.raynigon.unit.api.core.units.si.power.Watt()
        // speed
        "m/s"    | new com.raynigon.unit.api.core.units.si.speed.MetrePerSecond()
        "km/h"   | new com.raynigon.unit.api.core.units.si.speed.KilometrePerHour()
        // temperature
        "K"      | new com.raynigon.unit.api.core.units.si.temperature.Kelvin()
        "\u2103" | new com.raynigon.unit.api.core.units.si.temperature.Celsius()
        // time
        "s"      | new com.raynigon.unit.api.core.units.si.time.Second()
        "min"    | new com.raynigon.unit.api.core.units.si.time.Minute()
        "h"      | new com.raynigon.unit.api.core.units.si.time.Hour()
    }

    def 'metre conversion'() {

        when:
        def quantity = quantity(initialValue, unit)

        then:
        expectedValue == quantity.to(new com.raynigon.unit.api.core.units.si.length.Metre()).value.intValue()

        where:
        unit             | initialValue | expectedValue
        new com.raynigon.unit.api.core.units.si.length.Metre()      | 1    | 1
        new com.raynigon.unit.api.core.units.si.length.Millimetre() | 1000 | 1
        new com.raynigon.unit.api.core.units.si.length.Kilometre()  | 1    | 1000
    }

    def 'energy conversion'() {

        when:
        def quantity = quantity(initialValue, unit)

        then:
        expectedValue == quantity.to(new com.raynigon.unit.api.core.units.si.energy.Joule()).value.intValue()

        where:
        unit               | initialValue | expectedValue
        new com.raynigon.unit.api.core.units.si.energy.Joule()        | 1 | 1
        new com.raynigon.unit.api.core.units.si.energy.WattHour()     | 1 | 3600
        new com.raynigon.unit.api.core.units.si.energy.KiloWattHour() | 1 | 3600000
    }

    def 'speed conversion'() {

        when:
        def quantity = quantity(initialValue, unit)

        then:
        expectedValue == quantity.to(new com.raynigon.unit.api.core.units.si.speed.MetrePerSecond()).value.intValue()

        where:
        unit                   | initialValue | expectedValue
        new com.raynigon.unit.api.core.units.si.speed.MetrePerSecond()   | 1  | 1
        new com.raynigon.unit.api.core.units.si.speed.KilometrePerHour() | 36 | 10
    }
}
