package com.raynigon.unit_api.jpa.type

import com.raynigon.unit_api.core.annotation.QuantityShape
import com.raynigon.unit_api.core.units.si.length.Metre
import com.raynigon.unit_api.jpa.annotation.JpaUnit
import com.raynigon.unit_api.jpa.annotation.NoneQuantity
import com.raynigon.unit_api.jpa.exception.UnitNotFound
import org.hibernate.usertype.DynamicParameterizedType
import spock.lang.Specification

import javax.measure.Quantity
import javax.measure.quantity.Length
import java.lang.annotation.Annotation

class QuantityTypeSpec extends Specification {

    def 'registration keys are valid'(){

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
        JpaUnit jpaUnit = Mock()
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
        value | unit | quantityType       | shape                | expectedUnit | expectedShape
        "m"   | ""   | NoneQuantity.class | QuantityShape.NUMBER | new Metre() | QuantityShape.NUMBER
        ""    | "m"  | NoneQuantity.class | QuantityShape.NUMBER | new Metre()  | QuantityShape.NUMBER
        ""    | ""   | Length.class       | QuantityShape.NUMBER | new Metre()  | QuantityShape.NUMBER
        ""    | ""   | Length.class       | QuantityShape.STRING | new Metre()  | QuantityShape.STRING
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
        thrown(UnitNotFound)
    }

    def 'resolve unit failure with missing properties'() {

        given:
        QuantityType type = new QuantityType()

        and:
        DynamicParameterizedType.ParameterType params = Mock(DynamicParameterizedType.ParameterType)
        Properties props = new Properties()
        props.put(DynamicParameterizedType.PARAMETER_TYPE, params)

        and:
        JpaUnit jpaUnit = Mock()
        jpaUnit.value() >> ""
        jpaUnit.unit() >> ""
        jpaUnit.quantityType() >> NoneQuantity.class
        jpaUnit.shape() >> QuantityShape.NUMBER
        params.getAnnotationsMethod() >> ([jpaUnit] as Annotation[])

        when:
        type.setParameterValues(props)

        then:
        thrown(UnitNotFound)
    }

    def 'resolve unit failure with unknown unit'() {

        given:
        QuantityType type = new QuantityType()

        and:
        DynamicParameterizedType.ParameterType params = Mock(DynamicParameterizedType.ParameterType)
        Properties props = new Properties()
        props.put(DynamicParameterizedType.PARAMETER_TYPE, params)

        and:
        JpaUnit jpaUnit = Mock()
        jpaUnit.value() >> "qwerty"
        jpaUnit.unit() >> ""
        jpaUnit.quantityType() >> NoneQuantity.class
        jpaUnit.shape() >> QuantityShape.NUMBER
        params.getAnnotationsMethod() >> ([jpaUnit] as Annotation[])

        when:
        type.setParameterValues(props)

        then:
        thrown(UnitNotFound)
    }

    def 'resolve unit failure with unknown quantity class'() {

        given:
        QuantityType type = new QuantityType()

        and:
        DynamicParameterizedType.ParameterType params = Mock(DynamicParameterizedType.ParameterType)
        Properties props = new Properties()
        props.put(DynamicParameterizedType.PARAMETER_TYPE, params)

        and:
        JpaUnit jpaUnit = Mock()
        jpaUnit.value() >> ""
        jpaUnit.unit() >> ""
        jpaUnit.quantityType() >> DummyQuantity.class
        jpaUnit.shape() >> QuantityShape.NUMBER
        params.getAnnotationsMethod() >> ([jpaUnit] as Annotation[])

        when:
        type.setParameterValues(props)

        then:
        thrown(UnitNotFound)
    }

    static interface DummyQuantity extends Quantity<DummyQuantity> {}
}
