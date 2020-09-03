package com.raynigon.unit_api.kotlin

import javax.measure.Quantity

operator fun <T : Quantity<T>> Quantity<T>.compareTo(value: Quantity<T>): Int =
        this.toSystemUnit().value.toDouble().compareTo(value.toSystemUnit().value.toDouble())

operator fun <T : Quantity<T>> Quantity<T>.plus(value: Quantity<T>): Quantity<T> = this.add(value)

operator fun <T : Quantity<T>> Quantity<T>.minus(value: Quantity<T>): Quantity<T> = this.subtract(value)

operator fun <T : Quantity<T>, U : Quantity<U>> Quantity<T>.times(value: Quantity<U>): Quantity<*> = this.multiply(value)

operator fun <T : Quantity<T>, U : Quantity<U>> Quantity<T>.div(value: Quantity<U>): Quantity<*> = this.divide(value)

operator fun <T : Quantity<T>> Quantity<T>.times(value: Number): Quantity<T> = this.multiply(value)

operator fun <T : Quantity<T>> Quantity<T>.div(value: Number): Quantity<T> = this.divide(value)
