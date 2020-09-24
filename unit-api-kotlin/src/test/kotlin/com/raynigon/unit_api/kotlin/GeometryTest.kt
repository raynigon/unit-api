package com.raynigon.unit_api.kotlin

import com.raynigon.unit_api.core.service.UnitsApiService.quantity
import com.raynigon.unit_api.core.units.si.length.Metre
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GeometryTest {

    @Test
    fun `pythagorean theorem`() {
        val a = quantity(3, Metre())
        val b = quantity(4, Metre())
        val c = pythagoreanTheorem(a, b)
        assertEquals(5, c.to(Metre()).value)
    }
}
