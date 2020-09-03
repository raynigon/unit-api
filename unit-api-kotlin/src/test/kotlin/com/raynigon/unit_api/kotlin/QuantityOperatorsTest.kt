package com.raynigon.unit_api.kotlin

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import tech.units.indriya.AbstractUnit.ONE
import tech.units.indriya.quantity.Quantities.getQuantity
import tech.units.indriya.unit.Units.METRE

internal class QuantityOperatorsTest {

    @Test
    fun `comparison operator`() {
        val a = getQuantity(3, METRE)
        val b = getQuantity(4, METRE)
        val c = getQuantity(5, METRE)
        val d = getQuantity(4, METRE)
        assertTrue(a < b)
        assertFalse(a > b)
        assertTrue(c > b)
        assertFalse(c < b)
        assertTrue(b == d)
        assertTrue(b >= d)
        assertTrue(b <= d)
        assertTrue(a != b)
    }

    @Test
    fun `math operators`() {
        val a = getQuantity(2, METRE)
        val b = getQuantity(4, METRE)
        val c = getQuantity(3, METRE)

        assertEquals(getQuantity(6, METRE), a + b)
        assertEquals(getQuantity(2, METRE), b - a)
        val result = b * c
        assertEquals(12, result.getValue().toInt())
        assertEquals(METRE.pow(2), result.getUnit())
        assertEquals(getQuantity(2, ONE), b / a)
    }
}

