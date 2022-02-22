package com.raynigon.unit.api.jpa.type

import com.raynigon.unit.api.core.annotation.QuantityShape
import com.raynigon.unit.api.core.units.si.length.Metre
import spock.lang.Specification
import spock.lang.Unroll

import static com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.Metre


class QuantityJavaDescriptorSpec extends Specification {

    @Unroll
    def 'valid wrap #source'() {

        given:
        QuantityJavaDescriptor descriptor = new QuantityJavaDescriptor(new Metre(), QuantityShape.NUMBER)

        when:
        def result = descriptor.wrap(source as Object, null)

        then:
        expectedResult.unit == result.unit
        expectedResult.value.toDouble() == result.value.toDouble()

        where:
        source              | expectedResult
        Float.valueOf(1.1)  | Metre(1.1)
        Double.valueOf(1.2) | Metre(1.2)
        // TODO Byte.valueOf("2")   | Metre(2)
        // TODO Short.valueOf("3")  | Metre(3)
        Integer.valueOf(4)  | Metre(4)
        Long.valueOf(5)     | Metre(5)
        "6"                 | Metre(6)
        "7 m"               | Metre(7)
    }

    @Unroll
    def 'valid unwrap #source'() {

        given:
        QuantityJavaDescriptor descriptor = new QuantityJavaDescriptor(new Metre(), QuantityShape.NUMBER)

        when:
        def result = descriptor.unwrap(source, type, null)

        then:
        expectedResult == result

        where:
        source     | type          | expectedResult
        Metre(1.1) | Float.class   | 1.1f
        Metre(1.2) | Double.class  | 1.2
        Metre(2)   | Byte.class    | 2
        Metre(3)   | Short.class   | 3
        Metre(4)   | Integer.class | 4
        Metre(5)   | Long.class    | 5
        Metre(6)   | String.class  | "6 m"
    }

    def 'invalid wrap'() {
        given:
        QuantityJavaDescriptor descriptor = new QuantityJavaDescriptor(new Metre(), QuantityShape.NUMBER)

        when:
        descriptor.wrap([1, 2, 3] as int[], null)

        then:
        thrown(com.raynigon.unit.api.jpa.exception.QuantityPackingException)
    }

    def 'invalid unwrap'() {
        given:
        QuantityJavaDescriptor descriptor = new QuantityJavaDescriptor(new Metre(), QuantityShape.NUMBER)

        when:
        descriptor.unwrap(Metre(1), ArrayList.class, null)

        then:
        thrown(com.raynigon.unit.api.jpa.exception.QuantityPackingException)
    }
}
