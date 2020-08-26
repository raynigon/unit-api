package com.raynigon.unit_api.springdoc

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.type.SimpleType
import com.fasterxml.jackson.databind.type.TypeBindings
import com.fasterxml.jackson.databind.type.TypeFactory
import com.raynigon.unit_api.jackson.annotation.JsonUnit
import io.swagger.v3.core.converter.AnnotatedType
import io.swagger.v3.oas.models.media.Schema
import spock.lang.Specification

import javax.measure.Quantity
import javax.measure.quantity.Speed
import java.lang.annotation.Annotation

class UnitApiPropertyCustomizerSpec extends Specification {

    def 'convert quantity without annotation'(){

        given:
        def customizer = new UnitApiPropertyCustomizer()
        def property = new Schema()
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
        result.type == "string"
    }

    def 'convert quantity with annotation'(){

        given:
        def customizer = new UnitApiPropertyCustomizer()
        def property = new Schema()
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

        when:
        def result = customizer.customize(property, annotatedType)

        then:
        1 * jsonUnit.unit() >> "km/h"

        and:
        result.type == "string"
    }
}
