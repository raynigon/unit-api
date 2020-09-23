package com.raynigon.unit_api.jpa.type

import com.raynigon.unit_api.core.annotation.QuantityShape
import com.raynigon.unit_api.core.units.si.length.Metre
import com.raynigon.unit_api.jpa.exception.QuantityPackingException
import spock.lang.Specification
import spock.lang.Unroll

import static com.raynigon.unit_api.core.service.UnitsApiService.quantity


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
        Float.valueOf(1.1)  | quantity(1.1, new Metre())
        Double.valueOf(1.2) | quantity(1.2, new Metre())
        Byte.valueOf("2")   | quantity(2, new Metre())
        Short.valueOf("3")  | quantity(3, new Metre())
        Integer.valueOf(4)  | quantity(4, new Metre())
        Long.valueOf(5)     | quantity(5, new Metre())
        "6"                 | quantity(6, new Metre())
        "7 m"               | quantity(7, new Metre())
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
        source                     | type          | expectedResult
        quantity(1.1, new Metre()) | Float.class   | 1.1f
        quantity(1.2, new Metre()) | Double.class  | 1.2
        quantity(2, new Metre())   | Byte.class    | 2
        quantity(3, new Metre())   | Short.class   | 3
        quantity(4, new Metre())   | Integer.class | 4
        quantity(5, new Metre())   | Long.class    | 5
        quantity(6, new Metre())   | String.class  | "6 m"
    }

    def 'invalid wrap'() {
        given:
        QuantityJavaDescriptor descriptor = new QuantityJavaDescriptor(new Metre(), QuantityShape.NUMBER)

        when:
        descriptor.wrap([1, 2, 3] as int[], null)

        then:
        thrown(QuantityPackingException)
    }

    def 'invalid unwrap'() {
        given:
        QuantityJavaDescriptor descriptor = new QuantityJavaDescriptor(new Metre(), QuantityShape.NUMBER)

        when:
        descriptor.unwrap(quantity(1, new Metre()), ArrayList.class, null)

        then:
        thrown(QuantityPackingException)
    }
}
