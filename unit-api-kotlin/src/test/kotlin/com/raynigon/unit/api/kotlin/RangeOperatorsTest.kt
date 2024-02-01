package com.raynigon.unit.api.kotlin

import com.raynigon.unit.api.core.quantities.ComparableQuantity
import com.raynigon.unit.api.core.service.UnitsApiService.quantity
import com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.Metre
import com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.Watt
import com.raynigon.unit.api.core.units.si.dimensionless.One
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import javax.measure.Quantity
import javax.measure.UnconvertibleException
import javax.measure.quantity.Length
import javax.measure.quantity.Speed
import kotlin.math.sign
import com.raynigon.unit.api.core.units.si.length.Metre as MetreUnit

internal class RangeOperatorsTest {
    @Test
    fun `range to operator`() {
        assertTrue((Metre(0)..Metre(1)).contains(Metre(0.0) as ComparableQuantity<Length>))
        assertTrue((Metre(0)..Metre(1)).contains(Metre(0.5) as ComparableQuantity<Length>))
        assertTrue((Metre(0)..Metre(1)).contains(Metre(1.0) as ComparableQuantity<Length>))
        assertFalse((Metre(0)..Metre(1)).contains(Metre(1.01) as ComparableQuantity<Length>))
        assertFalse((Metre(0)..Metre(1)).contains(Metre(-0.01) as ComparableQuantity<Length>))
    }

    @Test
    fun `range until operator`() {
        assertTrue((Metre(0)..<Metre(1)).contains(Metre(0.0) as ComparableQuantity<Length>))
        assertTrue((Metre(0)..<Metre(1)).contains(Metre(0.5) as ComparableQuantity<Length>))
        assertTrue((Metre(0)..<Metre(1)).contains(Metre(0.9999) as ComparableQuantity<Length>))
        assertFalse((Metre(0)..<Metre(1)).contains(Metre(1.0) as ComparableQuantity<Length>))
        assertFalse((Metre(0)..<Metre(1)).contains(Metre(1.01) as ComparableQuantity<Length>))
        assertFalse((Metre(0)..<Metre(1)).contains(Metre(-0.01) as ComparableQuantity<Length>))
    }
}
