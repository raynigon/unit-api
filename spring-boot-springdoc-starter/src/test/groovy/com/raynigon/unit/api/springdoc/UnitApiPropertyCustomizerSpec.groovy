package com.raynigon.unit.api.springdoc

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.type.SimpleType
import com.fasterxml.jackson.databind.type.TypeBindings
import com.raynigon.unit.api.core.annotation.QuantityShape
import com.raynigon.unit.api.core.units.si.speed.KilometrePerHour
import com.raynigon.unit.api.jackson.annotation.JsonUnit
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
        annotatedType.type = new SimpleType(
                Quantity.class,
                TypeBindings.create(
                        Quantity.class,
                        [new SimpleType(Speed.class)] as JavaType[],
                ),
                null,
                null
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
        annotatedType.type = new SimpleType(
                Quantity.class,
                TypeBindings.create(
                        Quantity.class,
                        [new SimpleType(Speed.class)] as JavaType[],
                ),
                null,
                null
        )
        JsonUnit jsonUnit = Mock(JsonUnit)
        annotatedType.ctxAnnotations([
                jsonUnit
        ] as Annotation[])
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
        annotatedType.type = new SimpleType(
                Quantity.class,
                TypeBindings.create(
                        Quantity.class,
                        [new SimpleType(Speed.class)] as JavaType[],
                ),
                null,
                null
        )
        JsonUnit jsonUnit = Mock(JsonUnit)
        annotatedType.ctxAnnotations([
                jsonUnit
        ] as Annotation[])
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
        annotatedType.type = new SimpleType(
                Quantity.class,
                TypeBindings.create(
                        Quantity.class,
                        [new SimpleType(Speed.class)] as JavaType[],
                ),
                null,
                null
        )
        JsonUnit jsonUnit = Mock(JsonUnit)
        annotatedType.ctxAnnotations([
                jsonUnit
        ] as Annotation[])
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
}
