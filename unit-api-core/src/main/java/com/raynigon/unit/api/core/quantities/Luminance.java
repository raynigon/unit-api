package com.raynigon.unit.api.core.quantities;

import javax.measure.Quantity;
import javax.measure.quantity.Area;
import javax.measure.quantity.LuminousIntensity;

/**
 * Luminous intensity per unit area of light traveling in a given direction.
 * The system unit for this quantity is "cd/m²" (candela per square meter).
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 1.3
 *
 * @see LuminousIntensity
 * @see Area
 */
public interface Luminance extends Quantity<Luminance> {
}