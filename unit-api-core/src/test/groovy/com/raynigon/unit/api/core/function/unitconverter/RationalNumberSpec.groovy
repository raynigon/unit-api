package com.raynigon.unit.api.core.function.unitconverter


import spock.lang.Specification

import static ConverterTestUtils.closeTo

class RationalNumberSpec extends Specification {

    def equalityByValue() {
        given:
        com.raynigon.unit.api.core.function.RationalNumber rational_1 = com.raynigon.unit.api.core.function.RationalNumber.of(5, 3);
        com.raynigon.unit.api.core.function.RationalNumber rational_2 = com.raynigon.unit.api.core.function.RationalNumber.of(10, 6);

        when:
        true

        then:
        rational_1 == rational_2

    }

    def doubleNumberRepresentationWhenSmall() {
        given:
        com.raynigon.unit.api.core.function.RationalNumber rational_1 = com.raynigon.unit.api.core.function.RationalNumber.of(
                BigInteger.valueOf(1660538782L),
                new BigInteger("1000000000000000000000000000000000000"));

        com.raynigon.unit.api.core.function.RationalNumber rational_2 = com.raynigon.unit.api.core.function.RationalNumber.of(1.660538782E-27);
        when:
        true

        then:
        rational_1 == rational_2
    }

    def doubleNumberRepresentationWhenLarge() {
        given:
        com.raynigon.unit.api.core.function.RationalNumber rational_1 = com.raynigon.unit.api.core.function.RationalNumber.ofInteger(
                new BigInteger("123456789000000000000000000000000000"));

        com.raynigon.unit.api.core.function.RationalNumber rational_2 = com.raynigon.unit.api.core.function.RationalNumber.of(1.23456789E35);
        when:
        true

        then:
        rational_1 == rational_2

    }

    def doubleNumberRepresentationWhenIntegerUnscaled() {
        given:
        com.raynigon.unit.api.core.function.RationalNumber rational_1 = com.raynigon.unit.api.core.function.RationalNumber.ofInteger(3);

        com.raynigon.unit.api.core.function.RationalNumber rational_2 = com.raynigon.unit.api.core.function.RationalNumber.of(3.0);
        when:
        true

        then:
        rational_1 == rational_2

    }

    def veryLargeNumberRepresentation() {
        given:
        com.raynigon.unit.api.core.function.RationalNumber veryLargeRational = com.raynigon.unit.api.core.function.RationalNumber.of(Double.MAX_VALUE);

        double actual = veryLargeRational
                .divide(com.raynigon.unit.api.core.function.RationalNumber.ofInteger(Long.MAX_VALUE))
                .doubleValue();

        double expected = Double.MAX_VALUE / Long.MAX_VALUE;
        when:
        true

        then:
        closeTo(actual, expected, 1E-12);
    }

}
