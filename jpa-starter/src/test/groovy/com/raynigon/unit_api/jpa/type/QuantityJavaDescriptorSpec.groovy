package com.raynigon.unit_api.jpa.type

import com.raynigon.unit_api.core.annotation.QuantityShape
import com.raynigon.unit_api.jpa.exception.QuantityPackingException
import spock.lang.Specification
import spock.lang.Unroll
import tech.units.indriya.unit.Units

import static tech.units.indriya.quantity.Quantities.getQuantity

class QuantityJavaDescriptorSpec extends Specification {

    @Unroll
    def 'valid wrap #source'() {

        given:
        QuantityJavaDescriptor descriptor = new QuantityJavaDescriptor(Units.METRE, QuantityShape.NUMBER)

        when:
        def result = descriptor.wrap(source as Object, null)

        then:
        expectedResult.unit == result.unit
        expectedResult.value.toDouble() == result.value.toDouble()

        where:
        source              | expectedResult
        Float.valueOf(1.1)  | getQuantity(1.1, Units.METRE)
        Double.valueOf(1.2) | getQuantity(1.2, Units.METRE)
        Byte.valueOf("2")   | getQuantity(2, Units.METRE)
        Short.valueOf("3")  | getQuantity(3, Units.METRE)
        Integer.valueOf(4)  | getQuantity(4, Units.METRE)
        Long.valueOf(5)     | getQuantity(5, Units.METRE)
        "6"                 | getQuantity(6, Units.METRE)
        "7 m"               | getQuantity(7, Units.METRE)
    }

    @Unroll
    def 'valid unwrap #source'() {

        given:
        QuantityJavaDescriptor descriptor = new QuantityJavaDescriptor(Units.METRE, QuantityShape.NUMBER)

        when:
        def result = descriptor.unwrap(source, type, null)

        then:
        expectedResult == result

        where:
        source                        | type          | expectedResult
        getQuantity(1.1, Units.METRE) | Float.class   | 1.1f
        getQuantity(1.2, Units.METRE) | Double.class  | 1.2
        getQuantity(2, Units.METRE)   | Byte.class    | 2
        getQuantity(3, Units.METRE)   | Short.class   | 3
        getQuantity(4, Units.METRE)   | Integer.class | 4
        getQuantity(5, Units.METRE)   | Long.class    | 5
        getQuantity(6, Units.METRE)   | String.class  | "6 m"
    }

    def 'invalid wrap'() {
        given:
        QuantityJavaDescriptor descriptor = new QuantityJavaDescriptor(Units.METRE, QuantityShape.NUMBER)

        when:
        descriptor.wrap([1, 2, 3] as int[], null)

        then:
        thrown(QuantityPackingException)
    }

    def 'invalid unwrap'() {
        given:
        QuantityJavaDescriptor descriptor = new QuantityJavaDescriptor(Units.METRE, QuantityShape.NUMBER)

        when:
        descriptor.unwrap(getQuantity(1, Units.METRE), ArrayList.class, null)

        then:
        thrown(QuantityPackingException)
    }
}
