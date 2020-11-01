package com.raynigon.unit_api.core.function.unitconverter

import spock.lang.Specification

import javax.measure.UnitConverter

class AbstractConverterSpec extends Specification {

    AbstractConverterImplConverter converter = new AbstractConverterImplConverter()

    def setup() {
        converter = new AbstractConverterImplConverter()
    }

    def "Equals"() {
        // TODO
    }

    def "HashCode"() {
        // TODO
    }

    def "ToString"() {
        given:
        converter.identity = identity

        when:
        def result = converter.toString()

        then:
        result == expected

        where:
        identity | expected
        true     | "AbstractConverterImpl(IDENTITY)"
        false    | "AbstractConverterImpl(-1)"
    }

    def "TransformationLiteral"() {
        // TODO
    }

    def "InverseWhenNotIdentity"() {
        // TODO
    }

    def "Inverse"() {
        // TODO
    }

    def "CanReduceWith"() {
        // TODO
    }

    def "Reduce"() {
        // TODO
    }

    def "Concatenate"() {
        // TODO
    }

    def "GetConversionSteps"() {
        // TODO
    }

    def "ConvertWhenNotIdentity"() {
        // TODO
    }

    def "Convert"() {
        // TODO
    }

    def "TestConvert"() {
        // TODO
    }

    def "LinearFactor"() {
        // TODO
    }

    class AbstractConverterImplConverter extends AbstractConverter {

        public boolean identity = false
        public boolean linear = false

        @Override
        boolean equals(Object cvtr) {
            return cvtr == this
        }

        @Override
        int hashCode() {
            return -1
        }

        @Override
        protected String transformationLiteral() {
            return "-1"
        }

        @Override
        protected AbstractConverter inverseWhenNotIdentity() {
            return value - 1
        }

        @Override
        protected boolean canReduceWith(AbstractConverter that) {
            return false
        }

        @Override
        protected Number convertWhenNotIdentity(Number value) {
            return value + 1
        }

        @Override
        int compareTo(UnitConverter o) {
            return o.equals() ? 0 : -1
        }

        @Override
        boolean isIdentity() {
            return identity
        }

        @Override
        boolean isLinear() {
            return linear
        }
    }
}
