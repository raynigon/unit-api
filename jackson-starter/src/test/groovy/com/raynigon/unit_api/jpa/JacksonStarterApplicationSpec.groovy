package com.raynigon.unit_api.jpa

import com.raynigon.unit_api.jackson.helpers.BasicApplicationConfig
import com.raynigon.unit_api.jackson.helpers.BasicRestController
import com.raynigon.unit_api.jackson.helpers.BasicService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = [
                BasicApplicationConfig,
                BasicRestController,
                BasicService
        ]
)
class JacksonStarterApplicationSpec extends Specification {

    @Autowired
    BasicService service

    @Autowired
    TestRestTemplate restTemplate

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
    }
}
