package com.raynigon.unit.api.kotlin

import com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.Metre
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import com.raynigon.unit.api.core.units.si.length.Metre as MetreUnit

internal class GeometryTest {

    @Test
    fun `pythagorean theorem`() {
        val a = Metre(3)
        val b = Metre(4)
        val c = pythagoreanTheorem(a, b)
        assertEquals(5, c.to(MetreUnit()).value)
    }
}
