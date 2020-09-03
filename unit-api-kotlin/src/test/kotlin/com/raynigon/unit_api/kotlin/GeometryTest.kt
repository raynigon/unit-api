package com.raynigon.unit_api.kotlin

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import tech.units.indriya.quantity.Quantities.getQuantity
import tech.units.indriya.unit.Units
import tech.units.indriya.unit.Units.*

class GeometryTest{

    @Test
    fun `pythagorean theorem`(){
        val a = getQuantity(3, METRE)
        val b = getQuantity(4, METRE)
        val c = pythagoreanTheorem(a, b)
        assertEquals(5, c.to(METRE).value)
    }
}