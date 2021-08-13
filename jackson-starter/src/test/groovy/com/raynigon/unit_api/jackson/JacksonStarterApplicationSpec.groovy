package com.raynigon.unit_api.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.raynigon.unit_api.jackson.helpers.BasicApplicationConfig
import com.raynigon.unit_api.jackson.helpers.BasicRestController
import com.raynigon.unit_api.jackson.helpers.BasicService
import com.raynigon.unit_api.jackson.helpers.WeatherEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification

import static com.raynigon.unit_api.core.units.si.SISystemUnitsConstants.Celsius
import static com.raynigon.unit_api.core.units.si.SISystemUnitsConstants.MetrePerSecond
import static com.raynigon.unit_api.core.units.si.SISystemUnitsConstants.Percent

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = [
                BasicApplicationConfig,
                BasicRestController,
                BasicService
        ],
        properties = [
                "spring.jackson.unit-api.features.SYSTEM_UNIT_ON_MISSING_ANNOTATION=true"
        ]
)
class JacksonStarterApplicationSpec extends Specification {

    @Autowired
    BasicService service

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    ObjectMapper objectMapper

    def 'context setup works'() {
        expect:
        true
    }

    def 'entity creation works'() {

        given:
        def data = [
                "id"         : "1",
                "speed"      : 100,
                "temperature": 30
        ]

        when:
        def response = restTemplate.postForEntity("/api/basic-entity", data, Map.class)

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
        new WeatherEntity(Celsius(30), Percent(10))   | '{"temperature":30.0,"humidity":"10.0%"}'
        new WeatherEntity(Celsius(30), Percent(10.1)) | '{"temperature":30.0,"humidity":"10.1%"}'
        new WeatherEntity(Celsius(30), Percent(-10))  | '{"temperature":30.0,"humidity":"-10.0%"}'
    }
}
