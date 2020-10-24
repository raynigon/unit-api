package com.raynigon.unit_api.core.function

class ConverterTestUtils {

    public static boolean closeTo(Number actual, Number expected, Number delta) {
        return Math.abs(expected - actual) <= delta
    }
}
