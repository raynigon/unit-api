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
package com.raynigon.unit.api.core.function;

import com.raynigon.unit.api.core.function.numbersystem.DefaultNumberSystem;
import com.raynigon.unit.api.core.function.unitconverter.AbstractConverter;
import com.raynigon.unit.api.core.function.unitconverter.AddConverter;
import com.raynigon.unit.api.core.function.unitconverter.PowerOfIntConverter;
import com.raynigon.unit.api.core.function.unitconverter.PowerOfPiConverter;
import com.raynigon.unit.api.core.function.unitconverter.RationalConverter;
import com.raynigon.unit.api.core.function.unitconverter.DoubleMultiplyConverter;
import com.raynigon.unit.api.core.function.unitconverter.LogConverter;
import com.raynigon.unit.api.core.function.unitconverter.ExpConverter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Facade for internal number arithmetic.
 *
 * @author Andi Huber
 * @author Werner Keil
 * @version 1.4, August 21, 2019
 * @since 2.0
 */
@Slf4j
public final class CalculusUtils {

    /**
     * Memoization of Pi by number-of-digits.
     */
    private static final Map<Integer, BigDecimal> piCache = new HashMap<>();

    private static final Map<Class<? extends AbstractConverter>, Integer> normalFormOrder =
            new HashMap<>(9);

    /**
     * The default MathContext used for BigDecimal calculus.
     */
    public static final MathContext DEFAULT_MATH_CONTEXT = MathContext.DECIMAL128;

    /**
     * Exposes (non-final) the MathContext used for BigDecimal calculus.
     */
    public static MathContext MATH_CONTEXT = DEFAULT_MATH_CONTEXT;

    private static NumberSystem currentSystem;

    /**
     * All available {@link NumberSystem NumberSystems} used for Number arithmetic.
     */
    public static List<NumberSystem> getAvailableNumberSystems() {
        List<NumberSystem> systems = new ArrayList<>();
        ServiceLoader<NumberSystem> loader = ServiceLoader.load(NumberSystem.class);
        loader.forEach(
                NumberSystem -> {
                    systems.add(NumberSystem);
                });
        return systems;
    }

    /**
     * Returns the current {@link NumberSystem} used for Number arithmetic.
     */
    public static NumberSystem currentNumberSystem() {
        if (currentSystem == null) {
            currentSystem = new DefaultNumberSystem();
        }
        return currentSystem;
    }

    /**
     * Sets the current number system
     *
     * @param system the new current number system.
     * @see #currentNumberSystem
     */
    public static void setCurrentNumberSystem(NumberSystem system) {
        currentSystem = system;
    }

    /**
     * Pi calculation with Machin's formula.
     *
     * @see <a href= "http://mathworld.wolfram.com/PiFormulas.html" >Pi Formulas</a>
     */
    public static final class Pi {

        private static final BigDecimal TWO = new BigDecimal("2");
        private static final BigDecimal THREE = new BigDecimal("3");
        private static final BigDecimal FOUR = new BigDecimal("4");
        private static final BigDecimal FIVE = new BigDecimal("5");
        private static final BigDecimal TWO_HUNDRED_THIRTY_NINE = new BigDecimal("239");

        private Pi() {
        }

        public static BigDecimal ofNumDigits(int numDigits) {

            if (numDigits <= 0) {
                throw new IllegalArgumentException("numDigits is required to be greater than zero");
            }

            return piCache.computeIfAbsent(
                    numDigits,
                    __ -> {
                        final int calcDigits = numDigits + 10;
                        return FOUR.multiply(
                                        (FOUR.multiply(arccot(FIVE, calcDigits)))
                                                .subtract(arccot(TWO_HUNDRED_THIRTY_NINE, calcDigits)))
                                .setScale(numDigits, RoundingMode.DOWN);
                    });
        }

        /**
         * Compute arccot via the Taylor series expansion.
         */
        private static BigDecimal arccot(BigDecimal x, int numDigits) {
            BigDecimal unity = BigDecimal.ONE.setScale(numDigits, RoundingMode.DOWN);
            BigDecimal sum = unity.divide(x, RoundingMode.DOWN);
            BigDecimal xpower = new BigDecimal(sum.toString());
            BigDecimal term = null;
            int nTerms = 0;

            BigDecimal nearZero = BigDecimal.ONE.scaleByPowerOfTen(-numDigits);
            log.trace("arccot: ARGUMENT=" + x + " (nearZero=" + nearZero + ")");
            boolean add = false;
            // Add one term of Taylor series each time thru loop. Stop looping
            // when _term_
            // gets very close to zero.
            for (BigDecimal n = THREE; term == null || !term.equals(BigDecimal.ZERO); n = n.add(TWO)) {
                if (term != null && term.compareTo(nearZero) < 0) break;
                xpower = xpower.divide(x.pow(2), RoundingMode.DOWN);
                term = xpower.divide(n, RoundingMode.DOWN);
                sum = add ? sum.add(term) : sum.subtract(term);
                add = !add;
                log.trace("arccot: term=" + term);
                nTerms++;
            }
            log.trace("arccot: done. nTerms=" + nTerms);
            return sum;
        }
    }

    // -- NORMAL FORM TABLE OF COMPOSITION

    public static Map<Class<? extends AbstractConverter>, Integer> getNormalFormOrder() {
        synchronized (normalFormOrder) {
            if (normalFormOrder.isEmpty()) {
                normalFormOrder.put(AbstractConverter.IDENTITY.getClass(), 0);
                normalFormOrder.put(PowerOfIntConverter.class, 1);
                normalFormOrder.put(RationalConverter.class, 2);
                normalFormOrder.put(PowerOfPiConverter.class, 3);
                normalFormOrder.put(DoubleMultiplyConverter.class, 4);
                normalFormOrder.put(AddConverter.class, 5);
                normalFormOrder.put(LogConverter.class, 6);
                normalFormOrder.put(ExpConverter.class, 7);
                normalFormOrder.put(AbstractConverter.Pair.class, 99);
            }
        }

        return Collections.unmodifiableMap(normalFormOrder);
    }
}
