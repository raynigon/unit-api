package com.raynigon.unit_api.jackson.serializer

import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.type.TypeBindings
import com.raynigon.unit_api.core.annotation.QuantityShape
import com.raynigon.unit_api.core.units.si.length.Metre
import com.raynigon.unit_api.core.units.si.speed.KilometrePerHour
import com.raynigon.unit_api.core.units.si.temperature.Celsius
import com.raynigon.unit_api.jackson.UnitApiModule
import com.raynigon.unit_api.jackson.annotation.JsonUnit
import com.raynigon.unit_api.jackson.config.UnitApiConfig
import com.raynigon.unit_api.jackson.exception.UnknownUnitException
import spock.lang.Specification

import javax.measure.Quantity
import javax.measure.quantity.Length
import javax.measure.quantity.Speed
import javax.measure.quantity.Temperature

import static com.raynigon.unit_api.core.service.UnitsApiService.quantity

class QuantitySerializerSpec extends Specification {

    interface DummyQuantity extends Quantity<DummyQuantity> {}

    abstract class DummyType extends JavaType {

        public DummyType() {
            super(DummyQuantity.class, 0, null, null, false)
        }
    }

    def 'quantity deserialization with null unit'() {

        given:
        def serializer = new QuantitySerializer(new UnitApiConfig(0))

        and:
        SerializerProvider prov = Mock()
        BeanProperty property = Mock()

        and:
        JavaType propertyJavaType = Mock(DummyType)
        JavaType boundType = Mock(DummyType)
        TypeBindings propertyTypeBindings = TypeBindings.create(List, List.of(boundType))
        propertyJavaType.getBindings() >> propertyTypeBindings
        boundType.getRawClass() >> DummyQuantity.class

        and:
        def jsonUnit = Mock(JsonUnit)
        property.getAnnotation(JsonUnit.class) >> jsonUnit
        jsonUnit.value() >> JsonUnit.NoneUnit
        jsonUnit.unit() >> JsonUnit.NoneUnit

        when:
        serializer.createContextual(prov, property)

        then:
        thrown(UnknownUnitException)

        and:
        1 * property.getType() >> propertyJavaType
    }

    def 'number deserialization'() {

        given:
        def mapper = new ObjectMapper()
        mapper.registerModule(new UnitApiModule())

        and:
        def source = new BasicNumberEntity()
        source.id = "1"
        source.speed = quantity(100, new KilometrePerHour())

        when:
        def result = mapper.writeValueAsString(source)

        then:
        noExceptionThrown()

        and:
        result == '{"id":"1","speed":100.0}'
    }

    def 'string deserialization'() {

        given:
        def mapper = new ObjectMapper()
        mapper.registerModule(new UnitApiModule())

        and:
        def source = new BasicStringEntity()
        source.id = "1"
        source.temperature = quantity(30, new Celsius())

        when:
        def result = mapper.writeValueAsString(source)

        then:
        noExceptionThrown()

        and:
        result == '{"id":"1","temperature":"30 ℃"}'
    }

    def 'object deserialization'() {

        given:
        def mapper = new ObjectMapper()
        mapper.registerModule(new UnitApiModule())

        and:
        def source = new BasicObjectEntity()
        source.id = "1"
        source.distance = quantity(100, new Metre())

        when:
        def result = mapper.writeValueAsString(source)

        then:
        noExceptionThrown()

        and:
        result == '{"id":"1","distance":{"value":100.0,"unit":"m"}}'
    }


    class BasicNumberEntity {

        public String id;

        @JsonUnit(unit = KilometrePerHour)
        public Quantity<Speed> speed;
    }

    class BasicStringEntity {

        public String id;

        @JsonUnit(unit = Celsius, shape = QuantityShape.STRING)
        public Quantity<Temperature> temperature;
    }

    class BasicObjectEntity {

        public String id;

        @JsonUnit(unit = Metre, shape = QuantityShape.OBJECT)
        public Quantity<Length> distance;
    }
}
