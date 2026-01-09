package com.raynigon.unit.api.jackson

import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.web.client.RestClient
import tools.jackson.databind.ObjectMapper
import com.raynigon.unit.api.jackson.helpers.BasicApplicationConfig
import com.raynigon.unit.api.jackson.helpers.BasicRestController
import com.raynigon.unit.api.jackson.helpers.BasicService
import com.raynigon.unit.api.jackson.helpers.WeatherEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

import static com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.Celsius
import static com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.MetrePerSecond
import static com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.Percent

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = [
                BasicApplicationConfig,
                BasicRestController,
                BasicService
        ]
)
@TestPropertySource(
        properties = [
                "spring.jackson.unit-api.features.SYSTEM_UNIT_ON_MISSING_ANNOTATION=true"
        ]
)
class JacksonStarterApplicationSpec extends Specification {

    @LocalServerPort
    int port

    @Autowired
    BasicService service

    @Autowired
    RestClient.Builder restClientBuilder

    @Autowired
    ObjectMapper objectMapper

    RestClient restClient

    def setup() {
        restClient = restClientBuilder
                .baseUrl("http://localhost:$port")
                .build()
    }

    def 'context setup works'() {
        expect:
        objectMapper.registeredModules().any { it instanceof UnitApiModule }
    }

    def 'entity creation works'() {

        given:
        def data = [
                "id"         : "1",
                "speed"      : 100,
                "temperature": 30
        ]

        when:
        def response = restClient
                .post()
                .uri("/api/basic-entity")
                .body(data)
                .retrieve()
                .toEntity(Map.class)

        then:
        response.statusCode.'2xxSuccessful'

        and:
        service.getEntity("1") != null
        service.getEntity("1").speed == MetrePerSecond(100)
    }

    def 'entity with custom reader'() {

        given:
        true

        when:
        WeatherEntity result = objectMapper.readValue(input, WeatherEntity)

        then:
        expected.temperature == result.temperature
        expected.humidity == result.humidity

        where:
        input                                             | expected
        '{ "temperature": "30 °C", "humidity": "10 %" }'  | new WeatherEntity(Celsius(30), Percent(10))
        '{ "temperature": "30°C", "humidity": "10 %" }'   | new WeatherEntity(Celsius(30), Percent(10))
        '{ "temperature": "-30 °C", "humidity": "10 %" }' | new WeatherEntity(Celsius(-30), Percent(10))
        '{ "temperature": "-30°C", "humidity": "10 %" }'  | new WeatherEntity(Celsius(-30), Percent(10))
    }

    def 'entity with custom writer'() {

        when:
        def result = objectMapper.writeValueAsString(entity)

        then:
        expected == result

        where:
        entity                                        | expected
        new WeatherEntity(Celsius(30), Percent(10))   | '{"humidity":"10.0%","temperature":30.0}'
        new WeatherEntity(Celsius(30), Percent(10.1)) | '{"humidity":"10.1%","temperature":30.0}'
        new WeatherEntity(Celsius(30), Percent(-10))  | '{"humidity":"-10.0%","temperature":30.0}'
    }
}
