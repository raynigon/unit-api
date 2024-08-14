package com.raynigon.unit.api.core.units.binary;

import com.raynigon.unit.api.core.units.binary.bytes.Bit;
import com.raynigon.unit.api.core.units.binary.bytes.Byte;
import com.raynigon.unit.api.core.units.binary.bytes.Gibibyte;
import com.raynigon.unit.api.core.units.binary.bytes.Gigabyte;
import com.raynigon.unit.api.core.units.binary.bytes.Kibibyte;
import com.raynigon.unit.api.core.units.binary.bytes.Kilobyte;
import com.raynigon.unit.api.core.units.binary.bytes.Mebibyte;
import com.raynigon.unit.api.core.units.binary.bytes.Megabyte;
import com.raynigon.unit.api.core.units.binary.bytes.Tebibyte;
import com.raynigon.unit.api.core.units.binary.bytes.Terabyte;
import org.jetbrains.annotations.NotNull;

import javax.measure.Quantity;
import javax.measure.quantity.Dimensionless;

import static com.raynigon.unit.api.core.service.UnitsApiService.quantity;

public class BinarySystemUnitsConstants {
    public static final Bit Bit = new Bit();
    public static final Byte Byte = new Byte();
    public static final Gibibyte Gibibyte = new Gibibyte();
    public static final Gigabyte Gigabyte = new Gigabyte();
    public static final Kibibyte Kibibyte = new Kibibyte();
    public static final Kilobyte Kilobyte = new Kilobyte();
    public static final Mebibyte Mebibyte = new Mebibyte();
    public static final Megabyte Megabyte = new Megabyte();
    public static final Tebibyte Tebibyte = new Tebibyte();
    public static final Terabyte Terabyte = new Terabyte();

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Dimensionless> Bit(@NotNull Number value) {
        return quantity(value, Bit);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Dimensionless> Bits(@NotNull Number value) {
        return quantity(value, Bit);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Dimensionless> Byte(@NotNull Number value) {
        return quantity(value, Byte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Dimensionless> Bytes(@NotNull Number value) {
        return quantity(value, Byte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Dimensionless> Kilobyte(@NotNull Number value) {
        return quantity(value, Kilobyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Dimensionless> Kilobytes(@NotNull Number value) {
        return quantity(value, Kilobyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Dimensionless> Megabyte(@NotNull Number value) {
        return quantity(value, Megabyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Dimensionless> Megabytes(@NotNull Number value) {
        return quantity(value, Megabyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Dimensionless> Gigabyte(@NotNull Number value) {
        return quantity(value, Gigabyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Dimensionless> Gigabytes(@NotNull Number value) {
        return quantity(value, Gigabyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Dimensionless> Terabyte(@NotNull Number value) {
        return quantity(value, Terabyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Dimensionless> Terabytes(@NotNull Number value) {
        return quantity(value, Terabyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Dimensionless> Kibibyte(@NotNull Number value) {
        return quantity(value, Kibibyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Dimensionless> Kibibytes(@NotNull Number value) {
        return quantity(value, Kibibyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Dimensionless> Mebibyte(@NotNull Number value) {
        return quantity(value, Mebibyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Dimensionless> Mebibytes(@NotNull Number value) {
        return quantity(value, Mebibyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Dimensionless> Gibibyte(@NotNull Number value) {
        return quantity(value, Gibibyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Dimensionless> Gibibytes(@NotNull Number value) {
        return quantity(value, Gibibyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Dimensionless> Tebibyte(@NotNull Number value) {
        return quantity(value, Tebibyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Dimensionless> Tebibytes(@NotNull Number value) {
        return quantity(value, Tebibyte);
    }
}
