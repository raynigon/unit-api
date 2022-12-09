package com.raynigon.unit.api.kotlin

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
import javax.measure.quantity.Speed
import kotlin.math.sign
import com.raynigon.unit.api.core.units.si.length.Metre as MetreUnit

internal class QuantityOperatorsTest {

    @Test
    fun `comparison operator`() {
        val a = Metre(3)
        val b = Metre(4)
        val c = Metre(5)
        val d = Metre(4)
        assertTrue(a < b)
        assertFalse(a > b)
        assertTrue(c > b)
        assertFalse(c < b)
        assertTrue(b == d)
        assertTrue(b >= d)
        assertTrue(b <= d)
        assertTrue(a != b)

        // Definition 1:
        assertEquals(sign(a.compareTo(b).toDouble()), -sign(b.compareTo(a).toDouble()))

        // Definition 2:
        assertTrue(a < b && b < c)
        /* => */
        assertTrue(a < c)

        // Definition 3:

        /* <p>Finally, the implementor must ensure that {@code x.compareTo(y)==0}
        * implies that {@code sgn(x.compareTo(z)) == sgn(y.compareTo(z))}, for
        * all {@code z}.*/

        /* <p>It is strongly recommended, but <i>not</i> strictly required that
        * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
        * class that implements the {@code Comparable} interface and violates
        * this condition should clearly indicate this fact.  The recommended
        * language is "Note: this class has a natural ordering that is
        * inconsistent with equals."*/
    }
    
    @Test
    @Suppress("UNCHECKED_CAST")
    fun `invalid comparison fails`() {
        val a = Metre(3) as Quantity<*>
        val b = Watt(4) as Quantity<*>
        val c = a as Quantity<Speed>
        val d = b as Quantity<Speed>

        assertThrows(UnconvertibleException::class.java) { c < d }
    }

    @Test
    fun `math operators`() {
        val a = Metre(2)
        val b = Metre(4)
        val c = Metre(3)

        assertEquals(Metre(6), a + b)
        assertEquals(Metre(2), b - a)
        val result = b * c
        assertEquals(12, result.value.toInt())
        assertEquals(MetreUnit().pow(2), result.unit)
        assertEquals(quantity(2, One()), b / a)
    }
}
