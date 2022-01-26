package com.raynigon.unit.api.core.function.unitconverter


import spock.lang.Specification
import spock.lang.Unroll

import static ConverterTestUtils.closeTo

class AddConverterSpec extends Specification {

    private AddConverter converter;

    def setup() {
        converter = new AddConverter(10);
    }

    def 'two converters with the same offset are equal'() {
        given:
        AddConverter addConverter = new AddConverter(10);

        when:
        true

        then:
        addConverter != null
        converter == addConverter
    }

    def 'inverse the offset is the negative of the offset'() {
        given:
        def expected = new AddConverter(-10)

        when:
        def actual = converter.inverse()

        then:
        actual == expected
    }

    def 'AddConverter is not linear'() {
        expect:
        !converter.isLinear()
    }

    def 'offset getter returns the offset'() {
        expect:
        closeTo(converter.getOffset(), 10, 1E-12)
    }

    def 'value getter returns the offset'() {
        expect:
        closeTo(converter.getValue(), 10, 1E-12)
    }

    def 'toString method formats correctly'() {
        expect:
        converter.toString() == "Add(x -> x + 10)"
        converter.inverse().toString() == "Add(x -> x - 10)"
    }

    @Unroll
    def 'identity returns #expectedIdentity for offset #offset'() {
        expect:
        new AddConverter(offset).isIdentity() == expectedIdentity

        where:
        offset | expectedIdentity
        0      | true
        1      | false
        -1     | false
    }

    def 'conversion steps is a valid list'() {
        expect:
        converter.getConversionSteps() != null
    }
}
