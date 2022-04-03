package com.raynigon.unit.api.core.units.si

import com.raynigon.unit.api.core.units.si.dimensionless.One
import spock.lang.Specification
import spock.lang.Unroll

import java.lang.reflect.Method
import java.util.stream.Collectors

import static com.raynigon.unit.api.core.service.UnitsApiService.quantity

class SISystemUnitsConstantsSpec extends Specification {

    @Unroll
    def "constant #unit.getSimpleName() exists"() {
        given:
        Class constantsClass = SISystemUnitsConstants.class

        when:
        constantsClass.getField(unit.getSimpleName())

        then:
        noExceptionThrown()

        where:
        unit << units()
    }

    @Unroll
    def "factory method #unit.getSimpleName() exists"() {
        given:
        Class constantsClass = SISystemUnitsConstants.class

        when:
        constantsClass.getMethod(unit.getSimpleName(), Number.class)

        then:
        noExceptionThrown()

        where:
        unit << units()
    }

    @Unroll
    def "create a #unit.getSimpleName() quantity"() {
        given:
        Class constantsClass = SISystemUnitsConstants.class
        Method factoryMethod = constantsClass.getMethod(unit.getSimpleName(), Number.class)
        def unitInstance = unit.getConstructor().newInstance()

        when:
        def result = factoryMethod.invoke(null, 1)

        then:
        result == quantity(1, unitInstance)

        where:
        unit << units()
    }

    static def units() {
        return new SISystem()
                .getUnits()
                .stream()
                .map({ it.getClass() })
                .filter({ it != One.class })
                .sorted(Comparator.comparing({ it.getSimpleName() }))
                .distinct()
                .collect(Collectors.toList())
    }
}
