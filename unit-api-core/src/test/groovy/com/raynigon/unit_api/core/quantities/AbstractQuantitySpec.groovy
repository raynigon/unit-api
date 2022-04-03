package com.raynigon.unit_api.core.quantities

import com.raynigon.unit.api.core.quantities.AbstractQuantity
import com.raynigon.unit.api.core.quantities.ComparableQuantity
import org.jetbrains.annotations.NotNull
import spock.lang.Ignore
import spock.lang.Specification

import javax.measure.Quantity
import javax.measure.Unit

@Ignore
class AbstractQuantitySpec extends Specification {

    def 'get unit'() {
        expect:
        false
    }

    def 'get scale'() {
        expect:
        false
    }

    def 'convert quantity unit'() {
        expect:
        false
    }

    def 'isGreaterThan'() {
        expect:
        false
    }

    def 'isGreaterThanOrEqualTo'() {
        expect:
        false
    }

    def 'isLessThan'() {
        expect:
        false
    }

    def 'isLessThanOrEqualTo'() {
        expect:
        false
    }

    def 'isEquivalentTo'() {
        expect:
        false
    }

    def 'compareTo'() {
        expect:
        false
    }

    def 'equals method'() {
        expect:
        false
    }

    def 'hash code'() {
        expect:
        false
    }

    def 'to string'() {
        expect:
        false
    }

    def 'as type'() {
        expect:
        false
    }

    def 'has fraction'() {
        expect:
        false
    }

    def 'number system'() {
        expect:
        false
    }

    class DummyQuantity extends AbstractQuantity {

        protected DummyQuantity(Unit unit) {
            super(unit)
        }

        @Override
        Number getValue() {
            return null
        }

        @Override
        ComparableQuantity add(Quantity that) {
            return null
        }

        @Override
        ComparableQuantity subtract(Quantity that) {
            return null
        }

        @Override
        ComparableQuantity divide(Number that) {
            return null
        }

        @Override
        ComparableQuantity multiply(Number multiplier) {
            return null
        }

        @Override
        ComparableQuantity<?> inverse() {
            return null
        }

        @Override
        ComparableQuantity abs() {
            return null
        }

        @Override
        ComparableQuantity<?> multiply(Quantity multiplier) {
            return null
        }

        @Override
        Quantity negate() {
            return null
        }

        @Override
        ComparableQuantity<?> divide(Quantity that) {
            return null
        }
    }
}
