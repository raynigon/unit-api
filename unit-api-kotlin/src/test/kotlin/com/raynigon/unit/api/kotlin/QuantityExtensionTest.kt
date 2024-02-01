package com.raynigon.unit.api.kotlin

import com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.Kilometre
import com.raynigon.unit.api.core.units.si.length.Metre
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class QuantityExtensionTest {
    @Test
    fun `quantity to value`() {
        assertEquals(1.0, Kilometre(0.001).toValue(Metre()).toDouble())
        assertEquals(10.0, Kilometre(0.01).toValue(Metre()).toDouble())
        assertEquals(100.0, Kilometre(0.1).toValue(Metre()).toDouble())
        assertEquals(1000.0, Kilometre(1).toValue(Metre()).toDouble())
        assertEquals(1200.0, Kilometre(1.2).toValue(Metre()).toDouble())
        assertEquals(1234.5, Kilometre(1.2345).toValue(Metre()).toDouble())
    }

    @Test
    fun `range until operator`() {
        assertEquals(1.0, Kilometre(0.001).toDouble(Metre()))
        assertEquals(10.0, Kilometre(0.01).toDouble(Metre()))
        assertEquals(100.0, Kilometre(0.1).toDouble(Metre()))
        assertEquals(1000.0, Kilometre(1).toDouble(Metre()))
        assertEquals(1200.0, Kilometre(1.2).toDouble(Metre()))
        assertEquals(1234.5, Kilometre(1.2345).toDouble(Metre()))
    }
}
