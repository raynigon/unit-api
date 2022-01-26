package com.raynigon.unit_api.core.function.unitconverter

import spock.lang.Shared
import spock.lang.Specification

class IdentityMultiplyConverterSpec extends Specification {

    @Shared
    IdentityMultiplyConverter converter = new IdentityMultiplyConverter()

    def "IsIdentity"() {
        expect:
        converter.isIdentity()
    }

    def "Inverse"() {
        expect:
        converter.inverse() == converter
    }

    def "Convert"() {
        expect:
        converter.convert(value) == expected

        where:
        value | expected
        1     | 1
        0     | 0
        -1    | -1
        2     | 2
    }

    def "Concatenate"() {
        expect:
        converter.concatenate(value) == expected

        where:
        value     | expected
        null      | null
        converter | converter
    }

    def "GetConversionSteps"() {
        expect:
        converter.getConversionSteps() == []
    }

    def "GetValue"() {
        expect:
        converter.getValue() == 1
    }

    def "CompareTo"() {
        expect:
        converter.compareTo(other) == expected

        where:
        other     | expected
        converter | -1
    }

    def "Equals"() {
        expect:
        converter.equals(other) == expected

        where:
        other     | expected
        converter | true
    }

    def "HashCode"() {
        expect:
        converter.hashCode() == expected

        where:
        other     | expected
        converter | Objects.hashCode(1);
    }
}
