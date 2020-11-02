package com.raynigon.unit_api.core.function.unitconverter

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import javax.measure.UnitConverter

class AbstractConverterSpec extends Specification {

    @Shared
    AbstractConverterImplConverter converter = new AbstractConverterImplConverter()

    def setup() {
        converter.identity = false
        converter.linear = false
        converter.transformationLiteral = "-1"
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
        converter.transformationLiteral = tranformationLiteral

        when:
        def result = converter.toString()

        then:
        result == expected

        where:
        identity | tranformationLiteral | expected
        true     | "-1"                 | "AbstractConverterImpl(IDENTITY)"
        false    | "-1"                 | "AbstractConverterImpl(-1)"
        false    | ""                   | "AbstractConverterImpl"
        false    | null                 | "AbstractConverterImpl"
    }

    def "TransformationLiteral"() {
        expect:
        converter.transformationLiteral() == "-1"
    }

    def "InverseWhenNotIdentity"() {
        expect:
        converter.inverseWhenNotIdentity() == converter
    }

    def "Inverse"() {
        expect:
        converter.inverse() == converter
    }

    def "CanReduceWith"() {
        when:
        def result = converter.canReduceWith(converter)

        then:
        !result
    }

    def "Reduce"() {
        when:
        converter.reduce(converter)

        then:
        thrown(IllegalStateException)
    }

    @Unroll
    def "concatenate with identity #identity"() {

        given:
        converter.identity = identity

        when:
        def result = converter.concatenate(other)

        then:
        result.toString() == expected.toString()

        where:
        identity | other                              | expected
        true     | IdentityMultiplyConverter.INSTANCE | converter
        false    | nonIdentityConverter()             | new AbstractConverter.Pair(converter, nonIdentityConverter())
    }

    def "GetConversionSteps"() {
        expect:
        converter.getConversionSteps() == [converter]
    }

    def "ConvertWhenNotIdentity"() {
        expect:
        converter.convertWhenNotIdentity(1.0) == 2.0
    }

    def "Convert"() {
        expect:
        converter.convert(1.0) == 2.0
    }

    def "LinearFactor"() {
        expect:
        converter.linearFactor() == Optional.empty()
    }

    MultiplyConverter nonIdentityConverter() {
        MultiplyConverter converterMock = Mock()
        converterMock.isIdentity() >> false
        converterMock.getConversionSteps() >> []
        return converterMock
    }

    class AbstractConverterImplConverter extends AbstractConverter {

        public boolean identity = false
        public boolean linear = false
        public String transformationLiteral = "-1"

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
            return transformationLiteral
        }

        @Override
        protected AbstractConverter inverseWhenNotIdentity() {
            return this
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
