package com.raynigon.unit_api.springdoc

import com.raynigon.unit_api.springdoc.helpers.BasicApplicationConfig
import org.springdoc.core.customizers.PropertyCustomizer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = [
        BasicApplicationConfig.class
])
class SpringdocStarterApplicationSpec extends Specification {

    @Autowired
    Optional<List<PropertyCustomizer>> customizers

    def 'converter is registered'() {

        when:
        def result = customizers.orElse(null)

        then:
        result != null
        result.size() == 1
    }
}
