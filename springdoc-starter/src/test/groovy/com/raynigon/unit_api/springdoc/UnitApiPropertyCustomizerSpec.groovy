package com.raynigon.unit_api.springdoc

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.type.SimpleType
import com.fasterxml.jackson.databind.type.TypeBindings
import com.fasterxml.jackson.databind.type.TypeFactory
import com.raynigon.unit_api.core.units.si.speed.KilometrePerHour
import com.raynigon.unit_api.jackson.annotation.JsonUnit
import io.swagger.v3.core.converter.AnnotatedType
import io.swagger.v3.oas.models.media.Schema
import spock.lang.Specification

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
        result.type == "number | string"
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

        when:
        def result = customizer.customize(property, annotatedType)

        then:
        2 * jsonUnit.unit() >> KilometrePerHour.class

        and:
        result.type == "number | string"
        result.description == "speed is given in Kilometre per Hour (km/h)"
    }
}
