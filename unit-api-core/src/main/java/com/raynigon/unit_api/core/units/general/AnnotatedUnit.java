package com.raynigon.unit_api.core.units.general;

import java.util.Map;
import java.util.Objects;
import javax.measure.Dimension;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.UnitConverter;

/**
 * This class represents an annotated unit.
 *
 * <p>Instances of this class are created through the {@link AnnotatedUnit#of(Unit, String)} method.
 *
 * @param <Q> The type of the quantity measured by this unit.
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:werner@units.tech">Werner Keil</a>
 * @version 1.5, March 30, 2019
 * @since 1.0
 */
public class AnnotatedUnit<Q extends Quantity<Q>> extends AbstractUnit<Q> {

  /** */
  private static final long serialVersionUID = -5676462045106887728L;

  /** Holds the actual unit. */
  private final Unit<Q> actualUnit;

  /** Holds the annotation. */
  private final String annotation;

  /**
   * Creates an annotated unit equivalent to the specified unit.
   *
   * @param actualUnit the unit to be annotated.
   * @param annotation the annotation.
   */
  public AnnotatedUnit(Unit<Q> actualUnit, String annotation) {
    this.actualUnit =
        actualUnit instanceof AnnotatedUnit
            ? ((AnnotatedUnit<Q>) actualUnit).actualUnit
            : actualUnit;
    this.annotation = annotation;
  }

  /**
   * Returns the actual unit of this annotated unit (never an annotated unit itself).
   *
   * @return the actual unit.
   */
  public Unit<Q> getActualUnit() {
    return actualUnit;
  }

  /**
   * Returns the annotation of this annotated unit.
   *
   * @return the annotation.
   */
  public String getAnnotation() {
    return annotation;
  }

  @Override
  public String getSymbol() {
    return actualUnit.getSymbol();
  }

  @Override
  public Map<? extends Unit<?>, Integer> getBaseUnits() {
    return actualUnit.getBaseUnits();
  }

  @Override
  public Unit<Q> toSystemUnit() {
    return actualUnit.getSystemUnit();
  }

  @Override
  public Dimension getDimension() {
    return actualUnit.getDimension();
  }

  @Override
  public UnitConverter getSystemConverter() {
    return ((AbstractUnit<Q>) actualUnit).getSystemConverter();
  }

  @Override
  public int hashCode() {
    return Objects.hash(actualUnit, annotation);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj instanceof AnnotatedUnit<?>) {
      AnnotatedUnit<?> other = (AnnotatedUnit<?>) obj;
      return Objects.equals(actualUnit, other.actualUnit)
          && Objects.equals(annotation, other.annotation);
    }
    return false;
  }

  /**
   * Creates an annotated unit equivalent to the specified unit.
   *
   * @param actualUnit the unit to be annotated.
   * @param annotation the annotation.
   * @return the annotated unit.
   */
  public static <Q extends Quantity<Q>> AnnotatedUnit<Q> of(Unit<Q> actualUnit, String annotation) {
    return new AnnotatedUnit<>(actualUnit, annotation);
  }
}
