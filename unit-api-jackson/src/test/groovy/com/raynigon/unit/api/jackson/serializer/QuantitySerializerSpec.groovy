package com.raynigon.unit.api.jackson.serializer

import com.fasterxml.jackson.annotation.ObjectIdGenerator
import com.raynigon.unit.api.core.annotation.QuantityShape
import com.raynigon.unit.api.core.exception.UnitNotFoundException
import com.raynigon.unit.api.core.units.si.length.Metre
import com.raynigon.unit.api.core.units.si.speed.KilometrePerHour
import com.raynigon.unit.api.core.units.si.temperature.Celsius
import com.raynigon.unit.api.jackson.UnitApiModule
import com.raynigon.unit.api.jackson.annotation.JsonUnit
import com.raynigon.unit.api.jackson.config.UnitApiConfig
import com.raynigon.unit.api.jackson.exception.InvalidUnitException
import spock.lang.Specification
import tools.jackson.core.JacksonException
import tools.jackson.databind.*
import tools.jackson.databind.introspect.Annotated
import tools.jackson.databind.introspect.BeanPropertyDefinition
import tools.jackson.databind.json.JsonMapper
import tools.jackson.databind.ser.SerializerCache
import tools.jackson.databind.ser.SerializerFactory
import tools.jackson.databind.ser.WritableObjectId
import tools.jackson.databind.type.TypeBindings

import javax.measure.Quantity
import javax.measure.quantity.Length
import javax.measure.quantity.Speed
import javax.measure.quantity.Temperature

import static com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.Celsius as cCelsius
import static com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.KilometrePerHour as kmh
import static com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.Metre as cMetre
import static com.raynigon.unit.api.jackson.annotation.JsonUnit.NoneUnit

class QuantitySerializerSpec extends Specification {

    def 'quantity deserialization with null unit'() {

        given:
        def serializer = new QuantitySerializer(new UnitApiConfig(0))

        and:
        SerializationContext ctxt = new SimpleSerializationContext(Mock(SerializerFactory))
        BeanProperty property = Mock()

        and:
        JavaType propertyJavaType = new SimpleType(
                Quantity.class,
                TypeBindings.create(List, List.of( new SimpleType(DummyQuantity.class)))
        )

        and:
        def jsonUnit = Mock(JsonUnit)
        property.getAnnotation(JsonUnit.class) >> jsonUnit
        jsonUnit.value() >> NoneUnit
        jsonUnit.unit() >> NoneUnit

        when:
        serializer.createContextual(ctxt, property)

        then:
        thrown(UnitNotFoundException)

        and:
        1 * property.getType() >> propertyJavaType
    }

    def 'number deserialization'() {
        given:
        def mapper = JsonMapper.builder()
                .addModule(new UnitApiModule())
                .build()

        and:
        def source = new BasicNumberEntity()
        source.id = "1"
        source.speed = kmh(100)

        when:
        def result = mapper.writeValueAsString(source)

        then:
        noExceptionThrown()

        and:
        result == '{"id":"1","speed":100.0}'
    }

    def 'numeric string deserialization'() {

        given:
        def mapper = JsonMapper.builder()
                .addModule(new UnitApiModule())
                .build()

        and:
        def source = new BasicNumericEntity()
        source.id = "1"
        source.speed = kmh(100)

        when:
        def result = mapper.writeValueAsString(source)

        then:
        noExceptionThrown()

        and:
        result == '{"id":"1","speed":"100.0"}'
    }

    def 'string deserialization'() {

        given:
        def mapper = JsonMapper.builder()
                .addModule(new UnitApiModule())
                .build()

        and:
        def source = new BasicStringEntity()
        source.id = "1"
        source.temperature = cCelsius(30)

        when:
        def result = mapper.writeValueAsString(source)

        then:
        noExceptionThrown()

        and:
        result == '{"id":"1","temperature":"30 °C"}'
    }

    def 'object deserialization'() {

        given:
        def mapper = JsonMapper.builder()
                .addModule(new UnitApiModule())
                .build()

        and:
        def source = new BasicObjectEntity()
        source.id = "1"
        source.distance = cMetre(100)

        when:
        def result = mapper.writeValueAsString(source)

        then:
        noExceptionThrown()

        and:
        result == '{"distance":{"value":100.0,"unit":"m"},"id":"1"}'
    }

    interface DummyQuantity extends Quantity<DummyQuantity> {}

    abstract class DummyType extends JavaType {

        DummyType() {
            super(DummyQuantity.class, 0, null, null, false)
        }
    }

    class BasicNumberEntity {

        public String id

        @JsonUnit(unit = KilometrePerHour)
        public Quantity<Speed> speed
    }

    class BasicNumericEntity {

        public String id

        @JsonUnit(unit = KilometrePerHour, shape = QuantityShape.NUMERIC_STRING)
        public Quantity<Speed> speed
    }


    class BasicStringEntity {

        public String id

        @JsonUnit(unit = Celsius, shape = QuantityShape.STRING)
        public Quantity<Temperature> temperature
    }

    class BasicObjectEntity {

        public String id

        @JsonUnit(unit = Metre, shape = QuantityShape.OBJECT)
        public Quantity<Length> distance
    }

    class SimpleSerializationContext extends SerializationContext {

        protected SimpleSerializationContext(SerializerFactory f) {
            super(
                    null,
                    JsonMapper.builder().build().serializationConfig(),
                    null,
                    f,
                    new SerializerCache(),
            )
        }

        @Override
        WritableObjectId findObjectId(Object forPojo, ObjectIdGenerator<?> generatorType) {
            return null
        }

        @Override
        ValueSerializer<Object> serializerInstance(Annotated annotated, Object serDef) {
            return null
        }

        @Override
        Object includeFilterInstance(BeanPropertyDefinition forProperty, Class<?> filterClass) {
            return null
        }

        @Override
        boolean includeFilterSuppressNulls(Object filter) {
            return false
        }

        @Override
        def <T extends JsonNode> T valueToTree(Object fromValue) throws JacksonException {
            return null
        }
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
