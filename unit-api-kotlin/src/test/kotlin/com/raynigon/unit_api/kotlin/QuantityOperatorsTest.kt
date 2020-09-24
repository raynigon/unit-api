package com.raynigon.unit_api.kotlin

import com.raynigon.unit_api.core.service.UnitsApiService.quantity
import com.raynigon.unit_api.core.units.si.dimensionless.One
import com.raynigon.unit_api.core.units.si.length.Metre
import com.raynigon.unit_api.core.units.si.power.Watt
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import javax.measure.Quantity
import javax.measure.quantity.Speed
import kotlin.math.sign

internal class QuantityOperatorsTest {

    @Test
    fun `comparison operator`() {
        val a = quantity(3, Metre())
        val b = quantity(4, Metre())
        val c = quantity(5, Metre())
        val d = quantity(4, Metre())
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
    fun `invalid comparison fails`() {
        val a = quantity(3, Metre()) as Quantity<*>
        val b = quantity(4, Watt()) as Quantity<*>
        val c = a as Quantity<Speed>
        val d = b as Quantity<Speed>

        assertThrows(ClassCastException::class.java) { c < d }
    }

    @Test
    fun `math operators`() {
        val a = quantity(2, Metre())
        val b = quantity(4, Metre())
        val c = quantity(3, Metre())

        assertEquals(quantity(6, Metre()), a + b)
        assertEquals(quantity(2, Metre()), b - a)
        val result = b * c
        assertEquals(12, result.getValue().toInt())
        assertEquals(Metre().pow(2), result.getUnit())
        assertEquals(quantity(2, One()), b / a)
    }
}
