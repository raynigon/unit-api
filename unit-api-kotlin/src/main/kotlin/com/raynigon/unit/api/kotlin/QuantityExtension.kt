package com.raynigon.unit.api.kotlin

import com.raynigon.unit.api.core.math.QuantityMath
import com.raynigon.unit.api.core.units.general.IUnit
import javax.measure.Quantity

/**
 * This method is a shorthand for `Quantity.to(<UNIT>).value`.
 * @param unit    The Unit in which the value should be given
 * @return the value as Number
 */
fun <T : Quantity<T>> Quantity<T>.toValue(unit: IUnit<T>): Number {
    return this.to(unit).value
}

/**
 * This method is a shorthand for `Quantity.to(<UNIT>).value.toDouble`.
 * @param unit    The Unit in which the value should be given
 * @return the value as Double type
 */
fun <T : Quantity<T>> Quantity<T>.toDouble(unit: IUnit<T>): Double {
    return this.to(unit).value.toDouble()
}

/**
 * Clamp this Quantity between min and max.
 * This method returns a values that conforms to min &lt;= value &lt;= max
 *
 * @param min   the first quantity which should be compared
 * @param max   the other quantities which should be compared
 * @return a value between min and max
 */
fun <T : Quantity<T>> Quantity<T>.clamp(
    min: Quantity<T>,
    max: Quantity<T>,
): Quantity<T> {
    return QuantityMath.clamp(min, this, max)
}
