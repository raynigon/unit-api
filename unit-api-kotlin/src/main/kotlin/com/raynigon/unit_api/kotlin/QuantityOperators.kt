package com.raynigon.unit_api.kotlin

import org.jetbrains.annotations.Contract
import javax.measure.Quantity

/**
 * Compares this quantity with the specified quantity for order.  Returns a
 * negative integer, zero, or a positive integer as this quantity is less
 * than, equal to, or greater than the specified quantity.
 *
 * @param value   the quantity to be compared.
 * @return a negative integer, zero, or a positive integer as this quantity
 *          is less than, equal to, or greater than the specified quantity.
 *
 * @throws ClassCastException  the unit of this quantity cannot be compared
 * with the unit of the specified quantity
 */
@Contract(pure = true)
operator fun <T : Quantity<T>> Quantity<T>.compareTo(value: Quantity<T>): Int {
    val systemUnitSelf = this.toSystemUnit()
    val systemUnitOther = value.toSystemUnit()
    if (systemUnitSelf.unit != systemUnitOther.unit) {
        throw ClassCastException("Cannot cast from ${systemUnitSelf.unit} to ${systemUnitOther.unit}")
    }
    return systemUnitSelf.value.toDouble().compareTo(systemUnitOther.value.toDouble())
}

/**
 * Returns the sum of this {@code Quantity} with the one specified.
 * The result shall be as if this quantity and the given addend were
 * converted to {@linkplain Unit#getSystemUnit() system unit} before
 * to be added, and the result converted back to the unit of this
 * quantity or any other compatible unit at implementation choice.
 *
 * @param value     the {@code Quantity} to be added.
 * @return {@code this + value}.
 */
@Contract(pure = true)
operator fun <T : Quantity<T>> Quantity<T>.plus(value: Quantity<T>): Quantity<T> = this.add(value)

/**
 * Returns the difference between this {@code Quantity} and the one specified.
 * The result shall be as if this quantity and the given subtrahend were
 * converted to {@linkplain Unit#getSystemUnit() system unit} before
 * to be subtracted, and the result converted back to the unit of this
 * quantity or any other compatible unit at implementation choice.
 *
 * @param value the {@code Quantity} to be subtracted.
 * @return <code>this - value</code>.
 */
@Contract(pure = true)
operator fun <T : Quantity<T>> Quantity<T>.minus(value: Quantity<T>): Quantity<T> = this.subtract(value)

/**
 * Returns the product of this {@code Quantity} with the one specified.
 * The result shall be as if this quantity and the given multiplicand were
 * converted to {@linkplain Unit#getSystemUnit() system unit} before
 * to be multiplied, and the result converted back to the unit of this
 * quantity or any other compatible unit at implementation choice.
 *
 * @throws ClassCastException if the type of an element in the specified
 * operation is incompatible with this quantity
 *
 * @param value     the {@code Quantity} multiplicand.
 * @return <code>this * value</code>.
 */
@Contract(pure = true)
operator fun <T : Quantity<T>, U : Quantity<U>> Quantity<T>.times(value: Quantity<U>): Quantity<*> = this.multiply(value)

/**
 * Returns the quotient of this {@code Quantity} divided by the {@code Quantity}
 * specified.
 * The result shall be as if this quantity and the given divisor were
 * converted to {@linkplain Unit#getSystemUnit() system unit} before
 * to be divided, and the result converted back to the unit of this
 * quantity or any other compatible unit at implementation choice.
 *
 * @throws ClassCastException if the type of an element in the specified
 * operation is incompatible with this quantity
 *
 * @param value     the {@code Quantity} divisor.
 * @return <code>this / value</code>.
 */
@Contract(pure = true)
operator fun <T : Quantity<T>, U : Quantity<U>> Quantity<T>.div(value: Quantity<U>): Quantity<*> = this.divide(value)

/**
 * Returns the product of this {@code Quantity} with the {@code Number} value
 * specified.
 * The result shall be as if this quantity was converted to
 * {@linkplain Unit#getSystemUnit() system unit} before to be multiplied,
 * and the result converted back to the unit of this quantity or any
 * other compatible unit at implementation choice.
 *
 * @param value     the {@code Number} multiplicand.
 * @return <code>this * value</code>.
 */
@Contract(pure = true)
operator fun <T : Quantity<T>> Quantity<T>.times(value: Number): Quantity<T> = this.multiply(value)

/**
 * Returns the quotient of this {@code Quantity} divided by the {@code Number}
 * specified.
 * The result shall be as if this quantity was converted to
 * {@linkplain Unit#getSystemUnit() system unit} before to be divided,
 * and the result converted back to the unit of this quantity or any
 * other compatible unit at implementation choice.
 *
 * @param value     the {@code Number} divisor.
 * @return <code>this / value</code>.
 */
@Contract(pure = true)
operator fun <T : Quantity<T>> Quantity<T>.div(value: Number): Quantity<T> = this.divide(value)
