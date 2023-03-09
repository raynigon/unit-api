package com.raynigon.unit.api.kotlin

import com.raynigon.unit.api.core.units.si.time.NanoSecond
import com.raynigon.unit.api.core.units.si.time.Second
import java.time.Duration
import java.time.temporal.ChronoUnit
import javax.measure.Quantity
import javax.measure.quantity.Time
import com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.NanoSecond as cNanoSecond
import com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.Second as cSecond

/**
 * Convert a Java Duration to a Time Quantity.
 * @return the Quantity<Time> object with nano second resolution
 */
fun Duration.toQuantity(): Quantity<Time> {
    if (this.nano == 0) {
        return cSecond(this.seconds)
    }
    return (cSecond(this.seconds) + cNanoSecond(this.nano))
}

/**
 * Convert a Time Quantity to a Java Duration.
 * @return the Java Duration object with nano second resolution
 */
fun Quantity<Time>.toDuration(): Duration {
    val seconds: Long = this.to(Second()).value.toLong()
    val nanos: Long = (this - cSecond(seconds)).to(NanoSecond()).value.toLong()
    return Duration.of(seconds, ChronoUnit.SECONDS)
        .plus(Duration.of(nanos, ChronoUnit.NANOS))
}
