package com.raynigon.unit_api.kotlin

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import tech.units.indriya.AbstractUnit.ONE
import tech.units.indriya.quantity.Quantities.getQuantity
import tech.units.indriya.unit.Units.METRE
import tech.units.indriya.unit.Units.WATT
import javax.measure.Quantity
import javax.measure.quantity.Speed
import kotlin.math.sign

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
        val a = getQuantity(3, METRE) as Quantity<*>
        val b = getQuantity(4, WATT) as Quantity<*>
        val c = a as Quantity<Speed>
        val d = b as Quantity<Speed>

        assertThrows(ClassCastException::class.java) { c < d }
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

