package com.raynigon.unit.api.core.units.binary;

import com.raynigon.unit.api.core.units.binary.bytes.Byte;
import com.raynigon.unit.api.core.units.binary.bytes.*;
import org.jetbrains.annotations.NotNull;

import javax.measure.Quantity;

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
    public static @NotNull Quantity<Data> Bit(@NotNull Number value) {
        return quantity(value, Bit);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Data> Bits(@NotNull Number value) {
        return quantity(value, Bit);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Data> Byte(@NotNull Number value) {
        return quantity(value, Byte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Data> Bytes(@NotNull Number value) {
        return quantity(value, Byte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Data> Kilobyte(@NotNull Number value) {
        return quantity(value, Kilobyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Data> Kilobytes(@NotNull Number value) {
        return quantity(value, Kilobyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Data> Megabyte(@NotNull Number value) {
        return quantity(value, Megabyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Data> Megabytes(@NotNull Number value) {
        return quantity(value, Megabyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Data> Gigabyte(@NotNull Number value) {
        return quantity(value, Gigabyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Data> Gigabytes(@NotNull Number value) {
        return quantity(value, Gigabyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Data> Terabyte(@NotNull Number value) {
        return quantity(value, Terabyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Data> Terabytes(@NotNull Number value) {
        return quantity(value, Terabyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Data> Kibibyte(@NotNull Number value) {
        return quantity(value, Kibibyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Data> Kibibytes(@NotNull Number value) {
        return quantity(value, Kibibyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Data> Mebibyte(@NotNull Number value) {
        return quantity(value, Mebibyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Data> Mebibytes(@NotNull Number value) {
        return quantity(value, Mebibyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Data> Gibibyte(@NotNull Number value) {
        return quantity(value, Gibibyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Data> Gibibytes(@NotNull Number value) {
        return quantity(value, Gibibyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Data> Tebibyte(@NotNull Number value) {
        return quantity(value, Tebibyte);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Data> Tebibytes(@NotNull Number value) {
        return quantity(value, Tebibyte);
    }
}
