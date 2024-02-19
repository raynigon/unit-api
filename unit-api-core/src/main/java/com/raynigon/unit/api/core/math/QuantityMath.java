package com.raynigon.unit.api.core.math;

import com.raynigon.unit.api.core.quantities.ComparableQuantity;
import org.jetbrains.annotations.NotNull;

import javax.measure.Quantity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class contains only static methods,
 * which can be used for mathematical operations on Quantity objects.
 */
public class QuantityMath {

    /**
     * Compares the two quantities and returns the smaller one.
     *
     * @param arg0 the first quantity which should be compared
     * @param arg1 the second quantity which should be compared
     * @return the smallest of both given quantities
     */
    public static <Q extends Quantity<Q>> Quantity<Q> min(@NotNull Quantity<Q> arg0, @NotNull Quantity<Q> arg1) {
        Objects.requireNonNull(arg0);
        Objects.requireNonNull(arg1);
        int comparison = ((ComparableQuantity<Q>) arg0).compareTo(arg1);
        if (comparison < 0) {
            return arg0;
        }
        return arg1;
    }


    /**
     * Compares the given quantities and returns the smaller one.
     *
     * @param arg0 the first quantity which should be compared
     * @param argX the other quantities which should be compared
     * @return the smallest of all given quantities, or arg0 if argX is empty
     */
    @SafeVarargs
    public static <Q extends Quantity<Q>> Quantity<Q> min(@NotNull Quantity<Q> arg0, @NotNull Quantity<Q>... argX) {
        Objects.requireNonNull(arg0);
        List<Quantity<Q>> items = new ArrayList<>(1 + argX.length);
        items.add(arg0);
        items.addAll(List.of(argX));
        return items.stream()
                .filter(Objects::nonNull)
                .min((a, b) -> ((ComparableQuantity<Q>) a).compareTo(b))
                .orElse(arg0);
    }

    /**
     * Compares the two quantities and returns the biggest one.
     *
     * @param arg0 the first quantity which should be compared
     * @param arg1 the second quantity which should be compared
     * @return the biggest of both given quantities
     */
    public static <Q extends Quantity<Q>> Quantity<Q> max(@NotNull Quantity<Q> arg0, @NotNull Quantity<Q> arg1) {
        Objects.requireNonNull(arg0);
        Objects.requireNonNull(arg1);
        int comparison = ((ComparableQuantity<Q>) arg0).compareTo(arg1);
        if (comparison > 0) {
            return arg0;
        }
        return arg1;
    }

    /**
     * Compares the two quantities and returns the biggest one.
     *
     * @param arg0 the first quantity which should be compared
     * @param argX the other quantities which should be compared
     * @return the biggest of all given quantities, or arg0 if argX is empty
     */
    @SafeVarargs
    public static <Q extends Quantity<Q>> Quantity<Q> max(@NotNull Quantity<Q> arg0, @NotNull Quantity<Q>... argX) {
        Objects.requireNonNull(arg0);
        List<Quantity<Q>> items = new ArrayList<>(1 + argX.length);
        items.add(arg0);
        items.addAll(List.of(argX));
        return items.stream()
                .filter(Objects::nonNull)
                .max((a, b) -> ((ComparableQuantity<Q>) a).compareTo(b))
                .orElse(arg0);
    }

    /**
     * Clamp the value between min and max.
     * This method returns a values that conforms to min <= value <= max
     *
     * @param min   the first quantity which should be compared
     * @param value the first quantity which should be compared
     * @param max   the other quantities which should be compared
     * @return a value between min and max
     */
    public static <Q extends Quantity<Q>> Quantity<Q> clamp(@NotNull Quantity<Q> min, @NotNull Quantity<Q> value, @NotNull Quantity<Q> max) {
        Objects.requireNonNull(min);
        Objects.requireNonNull(value);
        Objects.requireNonNull(max);
        ComparableQuantity<Q> minComparable = (ComparableQuantity<Q>) min;
        ComparableQuantity<Q> valueComparable = (ComparableQuantity<Q>) value;
        ComparableQuantity<Q> maxComparable = (ComparableQuantity<Q>) max;
        if (minComparable.compareTo(maxComparable) > 0) {
            throw new IllegalArgumentException("The minimum has to be less than or equal to maximum");
        }
        if (minComparable.compareTo(valueComparable) > 0) {
            return minComparable;
        }
        if (maxComparable.compareTo(valueComparable) < 0) {
            return maxComparable;
        }
        return value;
    }
}
