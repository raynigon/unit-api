package com.raynigon.unit.api.kotlin

import com.raynigon.unit.api.core.quantities.ComparableQuantity
import javax.measure.Quantity
import org.jetbrains.annotations.Contract
import kotlin.jvm.Throws

/**
 * Creates a range from this Quantity value to the specified that value.
 * Numbers are compared with the ends of this range according to IEEE-754.
 * All used Quantities need to be comparable, otherwise an IllegalArgumentException is thrown.
 *
 * @param other     the end of the range.
 * @return the closed range for this Quantity objects
 * @throws  IllegalArgumentException if the given quantities are not comparable
 */
@Contract(pure = true)
@Throws(IllegalArgumentException::class)
operator fun <T : Quantity<T>> Quantity<T>.rangeTo(other: Quantity<T>): ClosedRange<ComparableQuantity<T>> {
    require(this is ComparableQuantity<T>)
    require(other is ComparableQuantity<T>)
    return ClosedQuantityRange(this, other)
}

/**
 * A closed range of values of type Double.
 * Numbers are compared with the ends of this range according to IEEE-754.
 */
data class ClosedQuantityRange<T : Quantity<T>>(
    override val start: ComparableQuantity<T>,
    override val endInclusive: ComparableQuantity<T>
) : ClosedRange<ComparableQuantity<T>>

/**
 * Creates an open-ended range from this Quantity value to the specified that value.
 * Numbers are compared with the ends of this range according to IEEE-754.
 * All used Quantities need to be comparable, otherwise an IllegalArgumentException is thrown.
 *
 * @param other     the end of the range.
 * @return the open end range for this Quantity objects
 * @throws  IllegalArgumentException if the given quantities are not comparable
 */
@Contract(pure = true)
@Throws(IllegalArgumentException::class)
operator fun <T : Quantity<T>> Quantity<T>.rangeUntil(other: Quantity<T>): OpenEndRange<ComparableQuantity<T>> {
    require(this is ComparableQuantity<T>)
    require(other is ComparableQuantity<T>)
    return OpenEndQuantityRange(this, other)
}

/**
 * An open-ended range of values of type Quantity.
 * Numbers are compared with the ends of this range according to IEEE-754.
 */
data class OpenEndQuantityRange<T : Quantity<T>>(
    override val start: ComparableQuantity<T>,
    override val endExclusive: ComparableQuantity<T>
) : OpenEndRange<ComparableQuantity<T>>