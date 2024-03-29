package com.raynigon.unit.api.kotlin

import com.raynigon.unit.api.core.service.UnitsApiService.quantity
import com.raynigon.unit.api.core.service.UnitsApiService.unit
import javax.measure.Quantity
import javax.measure.quantity.Length
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Implementation of the Pythagorean theorem (https://en.wikipedia.org/wiki/Pythagorean_theorem)
 * for Length Quantities. Calculates the length of a hypotenuse in a right-angled triangle.
 *  @param a   length of the first cathete in the triangle
 *  @param b   length of second cathete in the triangle
 *  @return length of hypotenuse in the triangle (c)
 */
fun pythagoreanTheorem(
    a: Quantity<Length>,
    b: Quantity<Length>,
): Quantity<Length> {
    val aInMetre = a.toSystemUnit().value.toDouble()
    val bInMetre = b.toSystemUnit().value.toDouble()
    val cInMetre = sqrt(aInMetre.pow(2.0) + bInMetre.pow(2.0))
    return quantity(cInMetre, unit(Length::class.java))
}
