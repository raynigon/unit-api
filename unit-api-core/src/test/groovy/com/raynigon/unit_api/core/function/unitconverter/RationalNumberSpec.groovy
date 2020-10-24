package com.raynigon.unit_api.core.function.unitconverter

import com.raynigon.unit_api.core.function.RationalNumber
import spock.lang.Specification

import static ConverterTestUtils.closeTo

class RationalNumberSpec extends Specification {

    def equalityByValue() {
        given:
        RationalNumber rational_1 = RationalNumber.of(5, 3);
        RationalNumber rational_2 = RationalNumber.of(10, 6);

        when:
        true

        then:
        rational_1 == rational_2

    }

    def doubleNumberRepresentationWhenSmall() {
        given:
        RationalNumber rational_1 = RationalNumber.of(
                BigInteger.valueOf(1660538782L),
                new BigInteger("1000000000000000000000000000000000000"));

        RationalNumber rational_2 = RationalNumber.of(1.660538782E-27);
        when:
        true

        then:
        rational_1 == rational_2
    }

    def doubleNumberRepresentationWhenLarge() {
        given:
        RationalNumber rational_1 = RationalNumber.ofInteger(
                new BigInteger("123456789000000000000000000000000000"));

        RationalNumber rational_2 = RationalNumber.of(1.23456789E35);
        when:
        true

        then:
        rational_1 == rational_2

    }

    def doubleNumberRepresentationWhenIntegerUnscaled() {
        given:
        RationalNumber rational_1 = RationalNumber.ofInteger(3);

        RationalNumber rational_2 = RationalNumber.of(3.0);
        when:
        true

        then:
        rational_1 == rational_2

    }

    def veryLargeNumberRepresentation() {
        given:
        RationalNumber veryLargeRational = RationalNumber.of(Double.MAX_VALUE);

        double actual = veryLargeRational
                .divide(RationalNumber.ofInteger(Long.MAX_VALUE))
                .doubleValue();

        double expected = Double.MAX_VALUE / Long.MAX_VALUE;
        when:
        true

        then:
        closeTo(actual, expected, 1E-12);
    }

}
