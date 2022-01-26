package com.raynigon.unit.api.kotlin

import com.raynigon.unit.api.core.units.si.time.NanoSecond
import com.raynigon.unit.api.core.units.si.time.Second
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Duration
import com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.NanoSecond as cNanoSecond
import com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.Second as cSecond

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
