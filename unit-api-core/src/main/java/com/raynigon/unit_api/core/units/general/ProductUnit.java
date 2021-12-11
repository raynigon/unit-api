package com.raynigon.unit_api.core.units.general;

/*
 * Units of Measurement Reference Implementation
 * Copyright (c) 2005-2020, Jean-Marie Dautelle, Werner Keil, Otavio Santana.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 *    and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of JSR-385, Indriya nor the names of their contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.raynigon.unit_api.core.function.unitconverter.AbstractConverter;
import com.raynigon.unit_api.core.function.Lazy;
import com.raynigon.unit_api.core.units.si.dimensionless.One;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.measure.Dimension;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.UnitConverter;

/**
 * This class represents units formed by the product of rational powers of existing physical units.
 *
 * <p>This class maintains the canonical form of this product (simplest form after factorization).
 * For example: <code>METRE.pow(2).divide(METRE)</code> returns <code>METRE</code>.
 *
 * @param <Q> The type of the quantity measured by this unit.
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:werner@units.tech">Werner Keil</a>
 * @author Andi Huber
 * @version 1.10, April 22, 2020
 * @since 1.0
 */
public class ProductUnit<Q extends Quantity<Q>> extends AbstractUnit<Q> {

    /**
     *
     */
    private static final long serialVersionUID = 962983585531030093L;

    /**
     * Holds the units composing this product unit.
     *
     * <p>Note: considered immutable after constructor was called
     */
    private final ProductUnitElement[] elements;

    // thread safe cache for the expensive hashCode calculation
    private transient Lazy<Integer> hashCode = new Lazy<>(this::calculateHashCode);

    /**
     * DefaultQuantityFactory constructor (used solely to create <code>ONE</code> instance).
     */
    public ProductUnit() {
        super("");
        elements = new ProductUnitElement[0];
    }

    /**
     * Copy constructor (allows for parameterization of product units).
     *
     * @param productUnit the product unit source.
     */
    public ProductUnit(ProductUnit<?> productUnit) {
        super(productUnit.getSymbol());
        this.elements = productUnit.elements;
    }

    /**
     * Product unit constructor.
     *
     * @param elements the product elements.
     */
    private ProductUnit(ProductUnitElement[] elements) {
        super(null);
        this.elements = elements;
    }

    /**
     * Returns the product of the specified units.
     *
     * @param left  the left unit operand.
     * @param right the right unit operand.
     * @return <code>left * right</code>
     */
    public static Unit<?> ofProduct(Unit<?> left, Unit<?> right) {
        ProductUnitElement[] leftElems;
        if (left instanceof ProductUnit<?>) {
            leftElems = ((ProductUnit<?>) left).elements;
        } else {
            leftElems = new ProductUnitElement[]{new ProductUnitElement(left, 1, 1)};
        }
        ProductUnitElement[] rightElems;
        if (right instanceof ProductUnit<?>) {
            rightElems = ((ProductUnit<?>) right).elements;
        } else {
            rightElems = new ProductUnitElement[]{new ProductUnitElement(right, 1, 1)};
        }
        return getInstance(leftElems, rightElems);
    }

    /**
     * Returns the quotient of the specified units.
     *
     * @param left  the dividend unit operand.
     * @param right the divisor unit operand.
     * @return <code>dividend / divisor</code>
     */
    public static Unit<?> ofQuotient(Unit<?> left, Unit<?> right) {
        ProductUnitElement[] leftElems;
        if (left instanceof ProductUnit<?>) leftElems = ((ProductUnit<?>) left).elements;
        else leftElems = new ProductUnitElement[]{new ProductUnitElement(left, 1, 1)};
        ProductUnitElement[] rightElems;
        if (right instanceof ProductUnit<?>) {
            ProductUnitElement[] elems = ((ProductUnit<?>) right).elements;
            rightElems = new ProductUnitElement[elems.length];
            for (int i = 0; i < elems.length; i++) {
                rightElems[i] = new ProductUnitElement(elems[i].getUnit(), -elems[i].getPow(), elems[i].getRoot());
            }
        } else rightElems = new ProductUnitElement[]{new ProductUnitElement(right, -1, 1)};
        return getInstance(leftElems, rightElems);
    }

    /**
     * Returns the product unit corresponding to the specified root of the specified unit.
     *
     * @param unit the unit.
     * @param n    the root's order (n &gt; 0).
     * @return <code>unit^(1/nn)</code>
     * @throws ArithmeticException if <code>n == 0</code>.
     */
    public static Unit<?> ofRoot(Unit<?> unit, int n) {
        ProductUnitElement[] unitElems;
        if (unit instanceof ProductUnit<?>) {
            ProductUnitElement[] elems = ((ProductUnit<?>) unit).elements;
            unitElems = new ProductUnitElement[elems.length];
            for (int i = 0; i < elems.length; i++) {
                int gcd = gcd(Math.abs(elems[i].getPow()), elems[i].getRoot() * n);
                unitElems[i] =
                        new ProductUnitElement(elems[i].getUnit(), elems[i].getPow() / gcd, elems[i].getRoot() * n / gcd);
            }
        } else unitElems = new ProductUnitElement[]{new ProductUnitElement(unit, 1, n)};
        return getInstance(unitElems, new ProductUnitElement[0]);
    }

    /**
     * Returns the product unit corresponding to this unit raised to the specified exponent.
     *
     * @param unit the unit.
     * @param n    the exponent (nn &gt; 0).
     * @return <code>unit^n</code>
     */
    public static Unit<?> ofPow(Unit<?> unit, int n) {
        ProductUnitElement[] unitElems;
        if (unit instanceof ProductUnit<?>) {
            ProductUnitElement[] elems = ((ProductUnit<?>) unit).elements;
            unitElems = new ProductUnitElement[elems.length];
            for (int i = 0; i < elems.length; i++) {
                int gcd = gcd(Math.abs(elems[i].getPow() * n), elems[i].getRoot());
                unitElems[i] =
                        new ProductUnitElement(elems[i].getUnit(), elems[i].getPow() * n / gcd, elems[i].getRoot() / gcd);
            }
        } else unitElems = new ProductUnitElement[]{new ProductUnitElement(unit, n, 1)};
        return getInstance(unitElems, new ProductUnitElement[0]);
    }

    @Override
    public Unit<?> pow(int n) {
        return ofPow(this, n);
    }

    /**
     * Returns the number of unit elements in this product.
     *
     * @return the number of unit elements.
     */
    public int getUnitCount() {
        return elements.length;
    }

    /**
     * Returns the unit element at the specified position.
     *
     * @param index the index of the unit element to return.
     * @return the unit element at the specified position.
     * @throws IndexOutOfBoundsException if index is out of range <code>
     *                                   (index &lt; 0 || index &gt;= getUnitCount())</code>.
     */
    public Unit<?> getUnit(int index) {
        return elements[index].getUnit();
    }

    /**
     * Returns the power exponent of the unit element at the specified position.
     *
     * @param index the index of the unit element.
     * @return the unit power exponent at the specified position.
     * @throws IndexOutOfBoundsException if index is out of range <code>
     *                                   (index &lt; 0 || index &gt;= getUnitCount())</code>.
     */
    public int getUnitPow(int index) {
        return elements[index].getPow();
    }

    /**
     * Returns the root exponent of the unit element at the specified position.
     *
     * @param index the index of the unit element.
     * @return the unit root exponent at the specified position.
     * @throws IndexOutOfBoundsException if index is out of range <code>
     *                                   (index &lt; 0 || index &gt;= getUnitCount())</code>.
     */
    public int getUnitRoot(int index) {
        return elements[index].getRoot();
    }

    @Override
    public Map<Unit<?>, Integer> getBaseUnits() {
        final Map<Unit<?>, Integer> units = new LinkedHashMap<>();
        for (int i = 0; i < getUnitCount(); i++) {
            units.put(getUnit(i), getUnitPow(i));
        }
        return units;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ProductUnit<?>) {
            final ProductUnit<?> other = ((ProductUnit<?>) obj);
            return ProductUnitElementUtil.arrayEqualsArbitraryOrder(this.elements, other.elements);
        }
        return false;
    }

    private int calculateHashCode() {
        return Objects.hash((Object[]) ProductUnitElementUtil.copyAndSort(elements));
    }

    @Override
    public int hashCode() {
        return hashCode.get(); // lazy and thread-safe
    }

    @SuppressWarnings("unchecked")
    @Override
    public Unit<Q> toSystemUnit() {
        Unit<?> systemUnit = new One();
        for (ProductUnitElement element : elements) {
            Unit<?> unit = element.getUnit().getSystemUnit();
            unit = unit.pow(element.getPow());
            unit = unit.root(element.getRoot());
            systemUnit = systemUnit.multiply(unit);
        }
        return (AbstractUnit<Q>) systemUnit;
    }

    @Override
    public UnitConverter getSystemConverter() {
        UnitConverter converter = AbstractConverter.IDENTITY;
        for (ProductUnitElement e : elements) {
            if (e.getUnit() instanceof AbstractUnit) {
                UnitConverter cvtr = ((AbstractUnit<?>) e.getUnit()).getSystemConverter();
                if (!(cvtr.isLinear()))
                    throw new UnsupportedOperationException(e.getUnit() + " is non-linear, cannot convert");
                if (e.getRoot() != 1)
                    throw new UnsupportedOperationException(
                            e.getUnit() + " holds a base unit with fractional exponent");
                int pow = e.getPow();
                if (pow < 0) { // Negative power.
                    pow = -pow;
                    cvtr = cvtr.inverse();
                }
                for (int j = 0; j < pow; j++) {
                    converter = converter.concatenate(cvtr);
                }
            }
        }
        return converter;
    }

    @Override
    public Dimension getDimension() {
        Dimension dimension = UnitDimension.NONE;
        for (int i = 0; i < this.getUnitCount(); i++) {
            Unit<?> unit = this.getUnit(i);
            if (this.elements != null && unit.getDimension() != null) {
                Dimension d = unit.getDimension().pow(this.getUnitPow(i)).root(this.getUnitRoot(i));
                dimension = dimension.multiply(d);
            }
        }
        return dimension;
    }

    @Override
    public String toString() {
        return Arrays.stream(elements).map(ProductUnitElement::toString).collect(Collectors.joining("*"));
    }

    /**
     * Returns the unit defined from the product of the specified elements.
     *
     * @param leftElems  left multiplicand elements.
     * @param rightElems right multiplicand elements.
     * @return the corresponding unit.
     */
    @SuppressWarnings("rawtypes")
    private static Unit<?> getInstance(
            ProductUnitElement[] leftElems, ProductUnitElement[] rightElems) {

        // Merges left elements with right elements.
        ProductUnitElement[] result = new ProductUnitElement[leftElems.length + rightElems.length];
        int resultIndex = 0;
        for (ProductUnitElement leftElem : leftElems) {
            Unit<?> unit = leftElem.getUnit();
            int p1 = leftElem.getPow();
            int r1 = leftElem.getRoot();
            int p2 = 0;
            int r2 = 1;
            for (ProductUnitElement rightElem : rightElems) {
                if (unit.equals(rightElem.getUnit())) {
                    p2 = rightElem.getPow();
                    r2 = rightElem.getRoot();
                    break; // No duplicate.
                }
            }
            int pow = p1 * r2 + p2 * r1;
            int root = r1 * r2;
            if (pow != 0) {
                int gcd = gcd(Math.abs(pow), root);
                result[resultIndex++] = new ProductUnitElement(unit, pow / gcd, root / gcd);
            }
        }

        // Appends remaining right elements not merged.
        for (ProductUnitElement rightElem : rightElems) {
            Unit<?> unit = rightElem.getUnit();
            boolean hasBeenMerged = false;
            for (ProductUnitElement leftElem : leftElems) {
                if (unit.equals(leftElem.getUnit())) {
                    hasBeenMerged = true;
                    break;
                }
            }
            if (!hasBeenMerged) result[resultIndex++] = rightElem;
        }

        // Returns or creates instance.
        if (resultIndex == 0) return new One();
        else if (resultIndex == 1 && result[0].getPow() == result[0].getRoot()) return result[0].getUnit();
        else {
            ProductUnitElement[] elems = new ProductUnitElement[resultIndex];
            System.arraycopy(result, 0, elems, 0, resultIndex);
            return new ProductUnit(elems);
        }
    }

    /**
     * Returns the greatest common divisor (Euclid's algorithm).
     *
     * @param m the first number.
     * @param n the second number.
     * @return the greatest common divisor.
     */
    private static int gcd(int m, int n) {
        return n == 0 ? m : gcd(n, m % n);
    }
}
