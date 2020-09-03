package com.raynigon.unit_api.kotlin

import com.raynigon.unit_api.core.service.UnitResolverService
import tech.units.indriya.quantity.Quantities.getQuantity
import javax.measure.Quantity
import javax.measure.quantity.Length
import kotlin.math.pow
import kotlin.math.sqrt

fun pythagoreanTheorem(a: Quantity<Length>, b: Quantity<Length>): Quantity<Length> {
    val aInMetre = a.toSystemUnit().value.toDouble()
    val bInMetre = b.toSystemUnit().value.toDouble()
    val cInMetre = sqrt(aInMetre.pow(2.0) + bInMetre.pow(2.0))
    return getQuantity(cInMetre, UnitResolverService.getInstance().getUnit(Length::class.java))
}