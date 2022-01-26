package com.raynigon.unit.api.core.function.unitconverter

class ConverterTestUtils {

    public static boolean closeTo(Number actual, Number expected, Number delta) {
        return Math.abs(expected - actual) <= delta
    }
}
