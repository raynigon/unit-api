package com.raynigon.unit.api.jpa.type

import com.raynigon.unit.api.core.annotation.QuantityShape
import com.raynigon.unit.api.core.exception.UnitNotFoundException
import com.raynigon.unit.api.core.units.general.IUnit
import com.raynigon.unit.api.core.units.si.length.Metre
import com.raynigon.unit.api.jpa.exception.MissingUnitAnnotationException
import org.hibernate.usertype.DynamicParameterizedType
import spock.lang.Specification

import javax.measure.Quantity
import javax.measure.quantity.Length
import java.lang.annotation.Annotation

class QuantityTypeSpec extends Specification {

    def 'registration keys are valid'() {

        expect:
        QuantityType.INSTANCE.getRegistrationKeys().contains(QuantityType.class.getSimpleName())
        QuantityType.INSTANCE.getRegistrationKeys().contains(Quantity.class.getName())
    }

    def 'successfully resolve unit'() {

        given:
        QuantityType type = new QuantityType()

        and:
        DynamicParameterizedType.ParameterType params = Mock(DynamicParameterizedType.ParameterType)
        Properties props = new Properties()
        props.put(DynamicParameterizedType.PARAMETER_TYPE, params)

        and:
        com.raynigon.unit.api.jpa.annotation.JpaUnit jpaUnit = Mock()
        jpaUnit.value() >> value
        jpaUnit.unit() >> unit
        jpaUnit.quantityType() >> quantityType
        jpaUnit.shape() >> shape
        params.getAnnotationsMethod() >> ([jpaUnit] as Annotation[])

        when:
        type.setParameterValues(props)

        and:
        QuantityJavaDescriptor javaType = type.getJavaTypeDescriptor() as QuantityJavaDescriptor

        then:
        expectedUnit == javaType.getUnit()
        expectedShape == javaType.getQuantityShape()

        where:
        value            | unit             | quantityType       | shape                | expectedUnit | expectedShape
        Metre                                                 | com.raynigon.unit.api.jpa.annotation.JpaUnit.NoneUnit | com.raynigon.unit.api.jpa.annotation.NoneQuantity.class | QuantityShape.NUMBER | new Metre() | QuantityShape.NUMBER
        com.raynigon.unit.api.jpa.annotation.JpaUnit.NoneUnit | Metre                                                 | com.raynigon.unit.api.jpa.annotation.NoneQuantity.class | QuantityShape.NUMBER | new Metre() | QuantityShape.NUMBER
        com.raynigon.unit.api.jpa.annotation.JpaUnit.NoneUnit | com.raynigon.unit.api.jpa.annotation.JpaUnit.NoneUnit | Length.class                                            | QuantityShape.NUMBER | new Metre() | QuantityShape.NUMBER
        com.raynigon.unit.api.jpa.annotation.JpaUnit.NoneUnit | com.raynigon.unit.api.jpa.annotation.JpaUnit.NoneUnit | Length.class                                            | QuantityShape.STRING | new Metre() | QuantityShape.STRING
    }

    def 'resolve unit failure with missing annotation'() {

        given:
        QuantityType type = new QuantityType()

        and:
        DynamicParameterizedType.ParameterType params = Mock(DynamicParameterizedType.ParameterType)
        Properties props = new Properties()
        props.put(DynamicParameterizedType.PARAMETER_TYPE, params)
        params.getAnnotationsMethod() >> ([] as Annotation[])

        when:
        type.setParameterValues(props)

        then:
        thrown(MissingUnitAnnotationException)
    }

    def 'resolve unit failure with missing properties'() {

        given:
        QuantityType type = new QuantityType()

        and:
        DynamicParameterizedType.ParameterType params = Mock(DynamicParameterizedType.ParameterType)
        Properties props = new Properties()
        props.put(DynamicParameterizedType.PARAMETER_TYPE, params)

        and:
        com.raynigon.unit.api.jpa.annotation.JpaUnit jpaUnit = Mock()
        jpaUnit.value() >> com.raynigon.unit.api.jpa.annotation.JpaUnit.NoneUnit
        jpaUnit.unit() >> com.raynigon.unit.api.jpa.annotation.JpaUnit.NoneUnit
        jpaUnit.quantityType() >> com.raynigon.unit.api.jpa.annotation.NoneQuantity.class
        jpaUnit.shape() >> QuantityShape.NUMBER
        params.getAnnotationsMethod() >> ([jpaUnit] as Annotation[])

        when:
        type.setParameterValues(props)

        then:
        thrown(UnitNotFoundException)
    }

    def 'resolve unit failure with unknown unit'() {

        given:
        QuantityType type = new QuantityType()

        and:
        DynamicParameterizedType.ParameterType params = Mock(DynamicParameterizedType.ParameterType)
        Properties props = new Properties()
        props.put(DynamicParameterizedType.PARAMETER_TYPE, params)

        and:
        com.raynigon.unit.api.jpa.annotation.JpaUnit jpaUnit = Mock()
        jpaUnit.value() >> DummyUnit.class
        jpaUnit.unit() >> com.raynigon.unit.api.jpa.annotation.JpaUnit.NoneUnit
        jpaUnit.quantityType() >> com.raynigon.unit.api.jpa.annotation.NoneQuantity.class
        jpaUnit.shape() >> QuantityShape.NUMBER
        params.getAnnotationsMethod() >> ([jpaUnit] as Annotation[])

        when:
        type.setParameterValues(props)

        then:
        thrown(UnitNotFoundException)
    }

    def 'resolve unit failure with unknown quantity class'() {

        given:
        QuantityType type = new QuantityType()

        and:
        DynamicParameterizedType.ParameterType params = Mock(DynamicParameterizedType.ParameterType)
        Properties props = new Properties()
        props.put(DynamicParameterizedType.PARAMETER_TYPE, params)

        and:
        com.raynigon.unit.api.jpa.annotation.JpaUnit jpaUnit = Mock()
        jpaUnit.value() >> com.raynigon.unit.api.jpa.annotation.JpaUnit.NoneUnit
        jpaUnit.unit() >> com.raynigon.unit.api.jpa.annotation.JpaUnit.NoneUnit
        jpaUnit.quantityType() >> DummyQuantity.class
        jpaUnit.shape() >> QuantityShape.NUMBER
        params.getAnnotationsMethod() >> ([jpaUnit] as Annotation[])

        when:
        type.setParameterValues(props)

        then:
        thrown(UnitNotFoundException)
    }

    static interface DummyQuantity extends Quantity<DummyQuantity> {}

    static interface DummyUnit extends IUnit<DummyQuantity> {}
}