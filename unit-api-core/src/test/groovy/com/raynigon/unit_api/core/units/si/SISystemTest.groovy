package com.raynigon.unit_api.core.units.si

import com.raynigon.unit_api.core.units.si.acceleration.MetrePerSquaredSecond
import com.raynigon.unit_api.core.units.si.energy.Joule
import com.raynigon.unit_api.core.units.si.energy.KiloWattHour
import com.raynigon.unit_api.core.units.si.energy.WattHour

import com.raynigon.unit_api.core.units.si.force.Newton
import com.raynigon.unit_api.core.units.si.length.Kilometre
import com.raynigon.unit_api.core.units.si.length.Metre
import com.raynigon.unit_api.core.units.si.length.Millimetre
import com.raynigon.unit_api.core.units.si.mass.Kilogram
import com.raynigon.unit_api.core.units.si.power.Watt
import com.raynigon.unit_api.core.units.si.speed.KilometrePerHour
import com.raynigon.unit_api.core.units.si.speed.MetrePerSecond
import com.raynigon.unit_api.core.units.si.temperature.Celsius
import com.raynigon.unit_api.core.units.si.temperature.Kelvin
import com.raynigon.unit_api.core.units.si.time.Hour
import com.raynigon.unit_api.core.units.si.time.Minute
import com.raynigon.unit_api.core.units.si.time.Second
import spock.lang.Specification
import spock.lang.Unroll
import tech.units.indriya.function.AddConverter
import tech.units.indriya.quantity.Quantities
import tech.units.indriya.unit.TransformedUnit

class SISystemTest extends Specification {

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
        "m/s²"   | new MetrePerSquaredSecond()
        // energy
        "J"      | new Joule()
        "Wh"     | new WattHour()
        "kWh"    | new KiloWattHour()
        // force
        "N"      | new Newton()
        // length
        "m"      | new Metre()
        "km"     | new Kilometre()
        "mm"     | new Millimetre()
        // mass
        "kg"     | new Kilogram()
        // power
        "W"      | new Watt()
        // speed
        "m/s"    | new MetrePerSecond()
        "km/h"   | new KilometrePerHour()
        // temperature
        "K"      | new Kelvin()
        "\u2103" | new Celsius()
        // time
        "s"      | new Second()
        "min"    | new Minute()
        "h"      | new Hour()
    }

    def 'metre conversion'() {

        when:
        def quantity = Quantities.getQuantity(initialValue, unit)

        then:
        expectedValue == quantity.to(new Metre()).value.intValue()

        where:
        unit             | initialValue | expectedValue
        new Metre()      | 1            | 1
        new Millimetre() | 1000         | 1
        new Kilometre()  | 1            | 1000
    }

    def 'energy conversion'() {

        when:
        def quantity = Quantities.getQuantity(initialValue, unit)

        then:
        expectedValue == quantity.to(new Joule()).value.intValue()

        where:
        unit               | initialValue | expectedValue
        new Joule()        | 1            | 1
        new WattHour()     | 1            | 3600
        new KiloWattHour() | 1            | 3600000
    }

    def 'speed conversion'() {

        when:
        def quantity = Quantities.getQuantity(initialValue, unit)

        then:
        expectedValue == quantity.to(new MetrePerSecond()).value.intValue()

        where:
        unit                   | initialValue | expectedValue
        new MetrePerSecond()   | 1            | 1
        new KilometrePerHour() | 36           | 10
    }
}
