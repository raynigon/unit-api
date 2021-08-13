package com.raynigon.unit_api.kotlin

import com.raynigon.unit_api.core.units.si.time.NanoSecond
import com.raynigon.unit_api.core.units.si.time.Second
import com.raynigon.unit_api.core.units.si.SISystemUnitsConstants.NanoSecond as cNanoSecond
import com.raynigon.unit_api.core.units.si.SISystemUnitsConstants.Second as cSecond
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.Duration

internal class DurationExtensionTest {

    @Test
    fun `duration with seconds to quantity`() {
        val duration = Duration.ofSeconds(10)
        val quantity = duration.toQuantity()

        assertEquals(10.0, quantity.to(Second()).value.toDouble())
    }

    @Test
    fun `quantity seconds to duration`() {
        val quantity = cSecond(10)
        val duration = quantity.toDuration()

        assertEquals(Duration.ofSeconds(10), duration)
    }

    @Test
    fun `duration with nanoseconds to quantity`() {
        val duration = Duration.ofNanos(10)
        val quantity = duration.toQuantity()

        assertEquals(10.0, quantity.to(NanoSecond()).value.toDouble())
    }

    @Test
    fun `quantity nanoseconds to duration`() {
        val quantity = cNanoSecond(10)
        val duration = quantity.toDuration()

        assertEquals(Duration.ofNanos(10), duration)
    }

    @Test
    fun `complex duration to quantity`() {
        val duration = Duration.ofNanos(10_100_000_000)
        val quantity = duration.toQuantity()

        assertEquals(10.1, quantity.to(Second()).value.toDouble())
    }

    @Test
    fun `complex quantity to duration`() {
        val quantity = cNanoSecond(10_100_000_000)
        val duration = quantity.toDuration()

        assertEquals(Duration.ofNanos(10_100_000_000), duration)
    }
}