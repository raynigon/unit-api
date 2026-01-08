package com.raynigon.unit.api.springdoc

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.type.TypeBindings
import com.raynigon.unit.api.core.annotation.QuantityShape
import com.raynigon.unit.api.core.units.si.power.Watt
import com.raynigon.unit.api.core.units.si.speed.KilometrePerHour
import com.raynigon.unit.api.jackson.annotation.JsonUnit
import com.raynigon.unit.api.validation.annotation.UnitMax
import com.raynigon.unit.api.validation.annotation.UnitMin
import io.swagger.v3.core.converter.AnnotatedType
import io.swagger.v3.oas.models.media.NumberSchema
import io.swagger.v3.oas.models.media.Schema
import io.swagger.v3.oas.models.media.StringSchema
import spock.lang.Specification
import spock.lang.Unroll

import javax.measure.Quantity
import javax.measure.quantity.Speed
import java.lang.annotation.Annotation

class UnitApiPropertyCustomizerSpec extends Specification {

    def 'convert quantity without annotation'() {

        given:
        def customizer = new UnitApiPropertyCustomizer()
        def property = new Schema()
        property.name = "speed"
        def annotatedType = new AnnotatedType()
        annotatedType.propertyName = "speed"
        annotatedType.type = new SimpleType(
                Quantity.class,
                TypeBindings.create(
                        Quantity.class,
                        [new SimpleType(Speed.class)] as JavaType[],
                ),
        )
        annotatedType.ctxAnnotations([] as Annotation[])

        when:
        def result = customizer.customize(property, annotatedType)

        then:
        result.type == "number"
        result.description == "speed is given in Metre per Second (m/s)"
    }

    def 'convert quantity with annotation'() {

        given:
        def customizer = new UnitApiPropertyCustomizer()
        def property = new Schema()
        property.name = "speed"
        def annotatedType = new AnnotatedType()
        annotatedType.propertyName = "speed"
        annotatedType.type = new SimpleType(
                Quantity.class,
                TypeBindings.create(
                        Quantity.class,
                        [new SimpleType(Speed.class)] as JavaType[],
                ),
        )
        JsonUnit jsonUnit = Mock(JsonUnit)
        annotatedType.ctxAnnotations([
                jsonUnit
        ] as Annotation[])
        jsonUnit.annotationType() >> JsonUnit.class
        jsonUnit.value() >> JsonUnit.NoneUnit.class
        jsonUnit.shape() >> QuantityShape.STRING

        when:
        def result = customizer.customize(property, annotatedType)

        then:
        2 * jsonUnit.unit() >> KilometrePerHour.class

        and:
        result.type == "string"
        result.description == "speed is given in Kilometre per Hour (km/h)"
    }

    def 'convert quantity with annotation as object'() {

        given:
        def customizer = new UnitApiPropertyCustomizer()
        def property = new Schema()
        property.name = "speed"
        def annotatedType = new AnnotatedType()
        annotatedType.propertyName = "speed"
        annotatedType.type = new SimpleType(
                Quantity.class,
                TypeBindings.create(
                        Quantity.class,
                        [new SimpleType(Speed.class)] as JavaType[],
                ),
        )
        JsonUnit jsonUnit = Mock(JsonUnit)
        annotatedType.ctxAnnotations([
                jsonUnit
        ] as Annotation[])
        jsonUnit.annotationType() >> JsonUnit.class
        jsonUnit.value() >> JsonUnit.NoneUnit.class
        jsonUnit.shape() >> QuantityShape.OBJECT

        when:
        def result = customizer.customize(property, annotatedType)

        then:
        2 * jsonUnit.unit() >> KilometrePerHour.class

        and:
        result.type == "quantity"
        result.properties == ["value": new NumberSchema(), "unit": new StringSchema()]
        result.description == "speed is given in Kilometre per Hour (km/h)"
    }

    @Unroll
    def 'handle existing description - #input'() {

        given:
        def customizer = new UnitApiPropertyCustomizer()
        def property = new Schema()
        property.name = "speed"
        property.description(input)
        def annotatedType = new AnnotatedType()
        annotatedType.propertyName = "speed"
        annotatedType.type = new SimpleType(
                Quantity.class,
                TypeBindings.create(
                        Quantity.class,
                        [new SimpleType(Speed.class)] as JavaType[],
                ),
        )
        JsonUnit jsonUnit = Mock(JsonUnit)
        annotatedType.ctxAnnotations([
                jsonUnit
        ] as Annotation[])
        jsonUnit.annotationType() >> JsonUnit.class
        jsonUnit.value() >> JsonUnit.NoneUnit.class
        jsonUnit.shape() >> QuantityShape.STRING

        when:
        def result = customizer.customize(property, annotatedType)

        then:
        2 * jsonUnit.unit() >> KilometrePerHour.class

        and:
        result.type == "string"
        result.description == expected

        where:
        input  | expected
        null   | "speed is given in Kilometre per Hour (km/h)"
        ""     | "speed is given in Kilometre per Hour (km/h)\n"
        "test" | "speed is given in Kilometre per Hour (km/h)\ntest"
    }

    def 'convert quantity with annotation and min'() {

        given:
        def customizer = new UnitApiPropertyCustomizer()
        def property = new Schema()
        property.name = "speed"

        and:
        def annotatedType = new AnnotatedType()
        annotatedType.propertyName = "speed"
        annotatedType.type = new SimpleType(
                Quantity.class,
                TypeBindings.create(
                        Quantity.class,
                        [new SimpleType(Speed.class)] as JavaType[],
                ),
        )

        and:
        JsonUnit jsonUnit = Mock(JsonUnit)
        jsonUnit.annotationType() >> JsonUnit.class
        jsonUnit.value() >> JsonUnit.NoneUnit.class
        jsonUnit.shape() >> QuantityShape.STRING

        and:
        UnitMin unitMin = Mock(UnitMin)
        unitMin.annotationType() >> UnitMin.class
        unitMin.value() >> 0.0
        unitMin.unit() >> Watt.class

        and:
        annotatedType.ctxAnnotations([
                jsonUnit,
                unitMin
        ] as Annotation[])


        when:
        def result = customizer.customize(property, annotatedType)

        then:
        2 * jsonUnit.unit() >> KilometrePerHour.class

        and:
        result.type == "string"
        result.description == "speed is given in Kilometre per Hour (km/h) and must be greater than 0.0 W"
    }

    def 'convert quantity with annotation and max'() {

        given:
        def customizer = new UnitApiPropertyCustomizer()
        def property = new Schema()
        property.name = "speed"

        and:
        def annotatedType = new AnnotatedType()
        annotatedType.propertyName = "speed"
        annotatedType.type = new SimpleType(
                Quantity.class,
                TypeBindings.create(
                        Quantity.class,
                        [new SimpleType(Speed.class)] as JavaType[],
                ),
        )
        and:
        JsonUnit jsonUnit = Mock(JsonUnit)
        jsonUnit.annotationType() >> JsonUnit.class
        jsonUnit.value() >> JsonUnit.NoneUnit.class
        jsonUnit.shape() >> QuantityShape.STRING

        and:
        UnitMax unitMax = Mock(UnitMax)
        unitMax.annotationType() >> unitMax.class
        unitMax.value() >> 10.0
        unitMax.unit() >> Watt.class

        and:
        annotatedType.ctxAnnotations([
                jsonUnit,
                unitMax
        ] as Annotation[])


        when:
        def result = customizer.customize(property, annotatedType)

        then:
        2 * jsonUnit.unit() >> KilometrePerHour.class

        and:
        result.type == "string"
        result.description == "speed is given in Kilometre per Hour (km/h) and must be less than 10.0 W"
    }

    def 'convert quantity with annotation and min + max'() {

        given:
        def customizer = new UnitApiPropertyCustomizer()
        def property = new Schema()
        property.name = "speed"

        and:
        def annotatedType = new AnnotatedType()
        annotatedType.propertyName = "speed"
        annotatedType.type = new SimpleType(
                Quantity.class,
                TypeBindings.create(
                        Quantity.class,
                        [new SimpleType(Speed.class)] as JavaType[],
                )
        )
        and:
        JsonUnit jsonUnit = Mock(JsonUnit)
        jsonUnit.annotationType() >> JsonUnit.class
        jsonUnit.value() >> JsonUnit.NoneUnit.class
        jsonUnit.shape() >> QuantityShape.STRING

        and:
        UnitMin unitMin = Mock(UnitMin)
        unitMin.annotationType() >> UnitMin.class
        unitMin.value() >> 0.0
        unitMin.unit() >> Watt.class

        and:
        UnitMax unitMax = Mock(UnitMax)
        unitMax.annotationType() >> UnitMax.class
        unitMax.value() >> 10.0
        unitMax.unit() >> Watt.class

        and:
        annotatedType.ctxAnnotations([
                jsonUnit,
                unitMin,
                unitMax
        ] as Annotation[])


        when:
        def result = customizer.customize(property, annotatedType)

        then:
        2 * jsonUnit.unit() >> KilometrePerHour.class

        and:
        result.type == "string"
        result.description == "speed is given in Kilometre per Hour (km/h) and must be between 0.0 W and 10.0 W"
    }

    class SimpleType extends JavaType {

        private TypeBindings bindings

        protected SimpleType(Class<?> raw) {
            super(raw, 0, null, null, false)
            this.bindings = null
        }

        protected SimpleType(Class<?> raw, TypeBindings bindings) {
            super(raw, 0, null, null, false)
            this.bindings = bindings
        }

        @Override
        JavaType withContentType(JavaType contentType) {
            return null
        }

        @Override
        JavaType withStaticTyping() {
            return null
        }

        @Override
        JavaType withTypeHandler(Object h) {
            return null
        }

        @Override
        JavaType withContentTypeHandler(Object h) {
            return null
        }

        @Override
        JavaType withValueHandler(Object h) {
            return null
        }

        @Override
        JavaType withContentValueHandler(Object h) {
            return null
        }

        @Override
        JavaType refine(Class<?> rawType, TypeBindings bindings, JavaType superClass, JavaType[] superInterfaces) {
            return null
        }

        @Override
        boolean isContainerType() {
            return false
        }

        @Override
        int containedTypeCount() {
            return 0
        }

        @Override
        JavaType containedType(int index) {
            return null
        }

        @Override
        String containedTypeName(int index) {
            return null
        }

        @Override
        String toCanonical() {
            return null
        }

        @Override
        TypeBindings getBindings() {
            return bindings
        }

        @Override
        JavaType findSuperType(Class<?> erasedTarget) {
            return null
        }

        @Override
        JavaType getSuperClass() {
            return null
        }

        @Override
        List<JavaType> getInterfaces() {
            return null
        }

        @Override
        JavaType[] findTypeParameters(Class<?> expType) {
            return null
        }

        @Override
        StringBuilder getGenericSignature(StringBuilder sb) {
            return null
        }

        @Override
        StringBuilder getErasedSignature(StringBuilder sb) {
            return null
        }

        @Override
        String toString() {
            return null
        }

        @Override
        boolean equals(Object o) {
            return false
        }
    }
}
