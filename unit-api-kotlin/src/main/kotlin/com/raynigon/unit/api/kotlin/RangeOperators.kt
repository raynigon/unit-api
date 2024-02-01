package com.raynigon.unit.api.kotlin

import com.raynigon.unit.api.core.quantities.ComparableQuantity
import javax.measure.Quantity
import org.jetbrains.annotations.Contract
import kotlin.jvm.Throws

/**
 * Create a closed Quantity Range with `this` as inclusive start and `other` as inclusive end.
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

data class ClosedQuantityRange<T : Quantity<T>>(
    override val start: ComparableQuantity<T>,
    override val endInclusive: ComparableQuantity<T>
) : ClosedRange<ComparableQuantity<T>>