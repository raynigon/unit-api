package com.raynigon.unit.api.jpa.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.raynigon.unit.api.core.annotation.QuantityShape;
import com.raynigon.unit.api.core.units.general.IUnit;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.measure.Quantity;
import javax.measure.quantity.Volume;

@Target({METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JpaUnit {

  /**
   * AliasFor(value="unit") Specifies the unit in which the Quantity is serialized/deserialized
   *
   * @return the unit which should be used for serialization/deserialization
   */
  Class<? extends IUnit<?>> value() default NoneUnit.class;

  /**
   * Specifies the unit in which the Quantity is serialized/deserialized
   *
   * @return the unit which should be used for serialization/deserialization
   */
  Class<? extends IUnit<?>> unit() default NoneUnit.class;

  /**
   * Serialize the Quantity as String
   *
   * @return the shape which should be used for serialization/deserialization
   */
  QuantityShape shape() default QuantityShape.NUMBER;

  /**
   * Specifies the quantityType which can be used to determine the system default unit
   *
   * @return the quantityType which should be used to determine the system unit for
   *     serialization/deserialization
   */
  Class<? extends Quantity<?>> quantityType() default NoneQuantity.class;

  interface NoneUnit extends IUnit<Volume>{}
}
