package com.raynigon.unit_api.kotlin

import com.raynigon.unit_api.core.units.si.time.NanoSecond
import com.raynigon.unit_api.core.units.si.time.Second
import com.raynigon.unit_api.core.units.si.SISystemUnitsConstants.NanoSecond as cNanoSecond
import com.raynigon.unit_api.core.units.si.SISystemUnitsConstants.Second as cSecond
import java.time.Duration
import java.time.temporal.ChronoUnit
import javax.measure.Quantity
import javax.measure.quantity.Time

fun Duration.toQuantity(): Quantity<Time> {
    if (this.nano == 0)
        return cSecond(this.seconds)
    return (cSecond(this.seconds) + cNanoSecond(this.nano))
}

fun Quantity<Time>.toDuration(): Duration {
    val seconds: Long = this.to(Second()).value.toLong()
    val nanos: Long = (this - cSecond(seconds)).to(NanoSecond()).value.toLong()
    return Duration.of(seconds, ChronoUnit.SECONDS)
        .plus(Duration.of(nanos, ChronoUnit.NANOS))
}