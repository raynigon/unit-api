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

import com.raynigon.unit_api.core.function.AbstractConverter;
import com.raynigon.unit_api.core.function.AddConverter;
import com.raynigon.unit_api.core.function.Calculator;
import com.raynigon.unit_api.core.function.CalculusUtils;
import com.raynigon.unit_api.core.function.DimensionalModel;
import com.raynigon.unit_api.core.function.MultiplyConverter;
import com.raynigon.unit_api.core.function.Nameable;
import com.raynigon.unit_api.core.function.PrefixOperator;
import com.raynigon.unit_api.core.function.RationalNumber;
import com.raynigon.unit_api.core.function.SymbolSupplier;
import com.raynigon.unit_api.core.units.si.dimensionless.One;

import javax.measure.Dimension;
import javax.measure.IncommensurableException;
import javax.measure.Prefix;
import javax.measure.Quantity;
import javax.measure.UnconvertibleException;
import javax.measure.Unit;
import javax.measure.UnitConverter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * The class represents units founded on the seven <b>SI</b> base units for
 * seven base quantities assumed to be mutually independent.
 * </p>
 *
 * <p>
 * For all physics units, unit conversions are symmetrical:
 * <code>u1.getConverterTo(u2).equals(u2.getConverterTo(u1).inverse())</code>.
 * Non-physical units (e.g. currency units) for which conversion is not
 * symmetrical should have their own separate class hierarchy and are considered
 * distinct (e.g. financial units), although they can always be combined with
 * physics units (e.g. "€/Kg", "$/h").
 * </p>
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:werner@units.tech">Werner Keil</a>
 * @version 2.4, December 14, 2019
 * @see <a href=
 * "http://en.wikipedia.org/wiki/International_System_of_Units">Wikipedia:
 * International System of Units</a>
 * @since 1.0
 */
public abstract class AbstractUnit<Q extends Quantity<Q>>
        implements ComparableUnit<Q>, Nameable, PrefixOperator<Q>, SymbolSupplier {

    /**
     *
     */
    private static final long serialVersionUID = -4344589505537030204L;

    /**
     * Holds the name.
     */
    protected String name;

    /**
     * Holds the symbol.
     */
    private String symbol;

    /**
     * Holds the unique symbols collection (base units or alternate units).
     */
    protected static final transient Map<String, Unit<?>> SYMBOL_TO_UNIT = new HashMap<>();

    /**
     * Default constructor.
     */
    protected AbstractUnit() {
    }

    /**
     * Constructor setting a symbol.
     *
     * @param symbol the unit symbol.
     */
    protected AbstractUnit(String symbol) {
        this.symbol = symbol;
    }

    protected Type getActualType() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return parameterizedType.getActualTypeArguments()[0].getClass().getGenericInterfaces()[0];
    }

    /**
     * Indicates if this unit belongs to the set of coherent SI units (unscaled SI
     * units).
     * <p>
     * The base and coherent derived units of the SI form a coherent set, designated
     * the set of coherent SI units. The word coherent is used here in the following
     * sense: when coherent units are used, equations between the numerical values
     * of quantities take exactly the same form as the equations between the
     * quantities themselves. Thus if only units from a coherent set are used,
     * conversion factors between units are never required.
     *
     * @return <code>equals(toSystemUnit())</code>
     */
    @Override
    public boolean isSystemUnit() {
        Unit<Q> sys = this.toSystemUnit();
        return this == sys || this.equals(sys);
    }

    /**
     * Returns the unscaled {@link com.raynigon.unit_api.core.units.si.SISystem} unit from which this unit is derived.
     * <p>
     * The SI unit can be be used to identify a quantity given the unit. For
     * example:<code> static boolean isAngularVelocity(AbstractUnit unit) {
     * return unit.toSystemUnit().equals(RADIAN.divide(SECOND)); } assert(REVOLUTION.divide(MINUTE).isAngularVelocity()); // Returns true. </code>
     *
     * @return the unscaled metric unit from which this unit is derived.
     */
    protected abstract Unit<Q> toSystemUnit();

    /**
     * Annotates the specified unit. Annotation does not change the unit semantic.
     * Annotations are often written between curly braces behind units.
     * Note: Annotation of system units are not considered themselves as system
     * units.
     *
     * @param annotation the unit annotation.
     * @return the annotated unit.
     */
    public final Unit<Q> annotate(String annotation) {
        return new AnnotatedUnit<>(this, annotation);
    }

    @Override
    public String toString() {
        return symbol != null ? symbol : super.toString();
    }

    // ///////////////////////////////////////////////////////
    // Implements javax.measure.Unit<Q> interface //
    // ///////////////////////////////////////////////////////

    /**
     * Returns the system unit (unscaled SI unit) from which this unit is derived.
     * They can be be used to identify a quantity given the unit. For example:<br>
     *
     * @return the unscaled metric unit from which this unit is derived.
     */
    @Override
    public final Unit<Q> getSystemUnit() {
        return toSystemUnit();
    }

    /**
     * Indicates if this unit is compatible with the unit specified. To be
     * compatible both units must be physics units having the same fundamental
     * dimension.
     *
     * @param that the other unit.
     * @return <code>true</code> if this unit and that unit have the same
     * fundamental dimension according to the current dimensional model;
     * <code>false</code> otherwise.
     */
    @Override
    public final boolean isCompatible(Unit<?> that) {
        return internalIsCompatible(that, true);
    }

    /**
     * Casts this unit to a parameterized unit of specified nature or throw a
     * ClassCastException if the dimension of the specified quantity and this unit's
     * dimension do not match (regardless whether or not the dimensions are
     * independent or not).
     *
     * @param type the quantity class identifying the nature of the unit.
     * @throws ClassCastException if the dimension of this unit is different from
     *                            the SI dimension of the specified type.
     */
    @SuppressWarnings("unchecked")
    @Override
    public final <T extends Quantity<T>> ComparableUnit<T> asType(Class<T> type) {
        Dimension typeDimension = UnitDimension.of(type);
        if (typeDimension != null && !typeDimension.equals(this.getDimension()))
            throw new ClassCastException("The unit: " + this + " is not compatible with quantities of type " + type);
        return (ComparableUnit<T>) this;
    }

    @Override
    public abstract Map<? extends Unit<?>, Integer> getBaseUnits();

    @Override
    public abstract Dimension getDimension();

    protected void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    protected void setSymbol(String s) {
        this.symbol = s;
    }

    @Override
    public final UnitConverter getConverterTo(Unit<Q> that) throws UnconvertibleException {
        return internalGetConverterTo(that, true);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public final UnitConverter getConverterToAny(Unit<?> that) throws IncommensurableException, UnconvertibleException {
        if (!isCompatible(that))
            throw new IncommensurableException(this + " is not compatible with " + that);
        ComparableUnit thatAbstr = (ComparableUnit) that; // Since both units are
        // compatible they must both be abstract units.
        final DimensionalModel model = DimensionalModel.current();
        Unit thisSystemUnit = this.getSystemUnit();
        UnitConverter thisToDimension = model.getDimensionalTransform(thisSystemUnit.getDimension())
                .concatenate(this.getSystemConverter());
        Unit thatSystemUnit = thatAbstr.getSystemUnit();
        UnitConverter thatToDimension = model.getDimensionalTransform(thatSystemUnit.getDimension())
                .concatenate(thatAbstr.getSystemConverter());
        return thatToDimension.inverse().concatenate(thisToDimension);
    }

    @Override
    @SuppressWarnings("unchecked")
    public final Unit<Q> alternate(String newSymbol) {
        if (this instanceof IUnit) {
            return new AlternateUnit<Q>(
                    ((IUnit<Q>) this).getSystemId(),
                    newSymbol,
                    "",
                    this,
                    ((IUnit<Q>) this).getQuantityType()
            );
        }
        throw new IllegalArgumentException("Unable to create alternate Unit from computed unit");
    }

    @Override
    public final Unit<Q> transform(UnitConverter operation) {
        Unit<Q> systemUnit = this.getSystemUnit();
        UnitConverter cvtr;
        if (this.isSystemUnit()) {
            cvtr = this.getSystemConverter().concatenate(operation);
        } else {
            cvtr = operation;
        }
        return cvtr.isIdentity() ? systemUnit : new TransformedUnit<>(null, this, systemUnit, cvtr);
    }

    @Override
    public final Unit<Q> shift(Number offset) {
        if (CalculusUtils.currentNumberSystem().isZero(offset))
            return this;
        return transform(new AddConverter(offset));
    }

    @Override
    public final Unit<Q> multiply(Number factor) {
        if (CalculusUtils.currentNumberSystem().isOne(factor))
            return this;
        return transform(MultiplyConverter.of(factor));
    }

    @Override
    public Unit<Q> shift(double offset) {
        return shift(RationalNumber.of(offset));
    }

    @Override
    public Unit<Q> multiply(double multiplier) {
        return multiply(RationalNumber.of(multiplier));
    }

    @Override
    public Unit<Q> divide(double divisor) {
        return divide(RationalNumber.of(divisor));
    }

    /**
     * Internal helper for isCompatible
     */
    private final boolean internalIsCompatible(Unit<?> that, boolean checkEquals) {
        if (checkEquals) {
            if (this == that || this.equals(that))
                return true;
        } else {
            if (this == that)
                return true;
        }
        if (!(that instanceof ComparableUnit))
            return false;
        Dimension thisDimension = this.getDimension();
        Dimension thatDimension = that.getDimension();
        if (thisDimension.equals(thatDimension))
            return true;
        DimensionalModel model = DimensionalModel.current(); // Use
        // dimensional
        // analysis
        // model.
        return model.getFundamentalDimension(thisDimension).equals(model.getFundamentalDimension(thatDimension));
    }

    protected final UnitConverter internalGetConverterTo(Unit<Q> that, boolean useEquals)
            throws UnconvertibleException {
        if (useEquals) {
            if (this == that || this.equals(that))
                return AbstractConverter.IDENTITY;
        } else {
            if (this == that)
                return AbstractConverter.IDENTITY;
        }
        Unit<Q> thisSystemUnit = this.getSystemUnit();
        Unit<Q> thatSystemUnit = that.getSystemUnit();
        if (!thisSystemUnit.equals(thatSystemUnit))
            try {
                return getConverterToAny(that);
            } catch (IncommensurableException e) {
                throw new UnconvertibleException(e);
            }
        UnitConverter thisToSI = this.getSystemConverter();
        UnitConverter thatToSI = that.getConverterTo(thatSystemUnit);
        return thatToSI.inverse().concatenate(thisToSI);
    }

    /**
     * Returns the product of this unit with the one specified.
     *
     * <p>
     * Note: If the specified unit (that) is not a physical unit, then
     * <code>that.multiply(this)</code> is returned.
     * </p>
     *
     * @param that the unit multiplicand.
     * @return <code>this * that</code>
     */
    @Override
    public final Unit<?> multiply(Unit<?> that) {
        if (that instanceof ComparableUnit)
            return multiply((ComparableUnit<?>) that);
        // return that.multiply(this); // Commutatif.
        return ProductUnit.ofProduct(this, that);
    }

    /**
     * Returns the product of this physical unit with the one specified.
     *
     * @param that the physical unit multiplicand.
     * @return <code>this * that</code>
     */
    protected final Unit<?> multiply(ComparableUnit<?> that) {
        if (this.equals(new One()))
            return that;
        if (that.equals(new One()))
            return this;
        return ProductUnit.ofProduct(this, that);
    }

    /**
     * Returns the inverse of this physical unit.
     *
     * @return <code>1 / this</code>
     */
    @Override
    public final Unit<?> inverse() {
        if (this.equals(new One()))
            return this;
        return ProductUnit.ofQuotient(new One(), this);
    }

    /**
     * Returns the result of dividing this unit by the specified divisor. If the
     * factor is an integer value, the division is exact. For example:
     *
     * <pre>
     * <code>
     *    QUART = GALLON_LIQUID_US.divide(4); // Exact definition.
     * </code>
     * </pre>
     *
     * @param divisor the divisor value.
     * @return this unit divided by the specified divisor.
     */
    @Override
    public final Unit<Q> divide(Number divisor) {
        if (CalculusUtils.currentNumberSystem().isOne(divisor))
            return this;
        Number factor = Calculator.of(divisor).reciprocal().peek();
        return transform(MultiplyConverter.of(factor));
    }

    /**
     * Returns the quotient of this unit with the one specified.
     *
     * @param that the unit divisor.
     * @return <code>this.multiply(that.inverse())</code>
     */
    @Override
    public final Unit<?> divide(Unit<?> that) {
        return this.multiply(that.inverse());
    }

    /**
     * Returns the quotient of this physical unit with the one specified.
     *
     * @param that the physical unit divisor.
     * @return <code>this.multiply(that.inverse())</code>
     */
    protected final Unit<?> divide(ComparableUnit<?> that) {
        return this.multiply(that.inverse());
    }

    /**
     * Returns a unit equals to the given root of this unit.
     *
     * @param n the root's order.
     * @return the result of taking the given root of this unit.
     * @throws ArithmeticException if <code>n == 0</code> or if this operation would
     *                             result in an unit with a fractional exponent.
     */
    @Override
    public final Unit<?> root(int n) {
        if (n > 0)
            return ProductUnit.ofRoot(this, n);
        else if (n == 0)
            throw new ArithmeticException("Root's order of zero");
        else
            // n < 0
            return new One().divide(this.root(-n));
    }

    /**
     * Returns a unit equals to this unit raised to an exponent.
     *
     * @param n the exponent.
     * @return the result of raising this unit to the exponent.
     */
    @Override
    public Unit<?> pow(int n) {
        if (n > 0)
            return this.multiply(this.pow(n - 1));
        else if (n == 0)
            return new One();
        else
            // n < 0
            return new One().divide(this.pow(-n));
    }

    @Override
    public Unit<Q> prefix(Prefix prefix) {
        return this.transform(MultiplyConverter.ofPrefix(prefix));
    }

    /**
     * Compares this unit to the specified unit. The default implementation compares
     * the name and symbol of both this unit and the specified unit, giving
     * precedence to the symbol.
     *
     * @return a negative integer, zero, or a positive integer as this unit is less
     * than, equal to, or greater than the specified unit.
     */
    @Override
    public int compareTo(Unit<Q> that) {
        int symbolComparison = compareToWithPossibleNullValues(getSymbol(), that.getSymbol());
        if (symbolComparison == 0) {
            return compareToWithPossibleNullValues(name, that.getName());
        } else {
            return symbolComparison;
        }
    }

    private int compareToWithPossibleNullValues(String a, String b) {
        if (a == null) {
            return (b == null) ? 0 : -1;
        } else {
            return (b == null) ? 1 : a.compareTo(b);
        }
    }

    @Override
    public boolean isEquivalentTo(Unit<Q> that) {
        return this.getConverterTo(that).isIdentity();
    }

    // //////////////////////////////////////////////////////////////
    // Ensures that sub-classes implement the hashCode method.
    // //////////////////////////////////////////////////////////////

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    /**
     * Utility class for number comparison and equality
     */
    protected static final class EqualizerUtils {
        /**
         * Indicates if this unit is considered equals to the specified object. order).
         *
         * @param u1 the object to compare for equality.
         * @param u2 the object to compare for equality.
         * @return <code>true</code> if <code>this</code> and <code>obj</code> are
         * considered equal; <code>false</code>otherwise.
         */
        public static boolean areEqual(@SuppressWarnings("rawtypes") ComparableUnit u1,
                                       @SuppressWarnings("rawtypes") ComparableUnit u2) {
            /*
             * if (u1 != null && u2 != null) { if (u1.getName() != null && u1.getSymbol() !=
             * null) { return u1.getName().equals(u2.getName()) &&
             * u1.getSymbol().equals(u2.getSymbol()) && u1.internalIsCompatible(u2, false);
             * } else if (u1.getSymbol() != null) { return
             * u1.getSymbol().equals(u2.getSymbol()) && u1.internalIsCompatible(u2, false);
             * } else { return u1.toString().equals(u2.toString()) &&
             * u1.internalIsCompatible(u2, false); } } else {
             */
            return (u1 != null && u1.equals(u2));
        }
    }
}

