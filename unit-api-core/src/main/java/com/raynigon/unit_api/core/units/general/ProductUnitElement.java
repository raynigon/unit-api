package com.raynigon.unit_api.core.units.general;

import javax.measure.Unit;
import java.io.Serializable;
import java.util.Objects;

/**
 * Inner product element represents a rational power of a single unit.
 */
final class ProductUnitElement implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 452938412398890507L;

    /**
     * Holds the single unit.
     */
    private final Unit<?> unit;

    /**
     * Holds the power exponent.
     */
    private final int pow;

    /**
     * Holds the root exponent.
     */
    private final int root;

    /**
     * Structural constructor.
     *
     * @param unit the unit.
     * @param pow  the power exponent.
     * @param root the root exponent.
     */
    ProductUnitElement(Unit<?> unit, int pow, int root) {
        this.unit = unit;
        this.pow = pow;
        this.root = root;
    }

    /**
     * Returns this element's unit.
     *
     * @return the single unit.
     */
    public Unit<?> getUnit() {
        return unit;
    }

    /**
     * Returns the power exponent. The power exponent can be negative but is always different from
     * zero.
     *
     * @return the power exponent of the single unit.
     */
    public int getPow() {
        return pow;
    }

    /**
     * Returns the root exponent. The root exponent is always greater than zero.
     *
     * @return the root exponent of the single unit.
     */
    public int getRoot() {
        return root;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ProductUnitElement other = (ProductUnitElement) o;

        if (!Objects.equals(this.pow, other.pow)) {
            return false;
        }
        if (!Objects.equals(this.root, other.root)) {
            return false;
        }
        return Objects.equals(this.unit, other.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit, pow, root);
    }

    @Override
    public String toString() {
        if (pow == 1 && root == 1) {
            return unit.toString();
        } else if (root != 1) {
            return unit.toString() + "^(" + pow + "/" + root + ")";
        } // else if (root == 1)
        return unit.toString() + "^" + pow;
    }
}
