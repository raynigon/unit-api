package com.github.raynigon.unit_api_starter.jackson

import com.github.raynigon.unit_api_starter.jackson.helpers.BasicApplicationConfig
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = [BasicApplicationConfig]
)
class SpringApplicationSpec extends Specification {


    def 'context setup works'() {
        expect:
        true
    }
}
