package com.raynigon.unit_api.jpa

import com.raynigon.unit_api.core.units.si.length.Kilometre
import com.raynigon.unit_api.core.units.si.length.Metre
import com.raynigon.unit_api.core.units.si.speed.KilometrePerHour
import com.raynigon.unit_api.core.units.si.temperature.Celsius
import com.raynigon.unit_api.jpa.helpers.BasicApplicationConfig
import com.raynigon.unit_api.jpa.helpers.BasicEntity
import com.raynigon.unit_api.jpa.helpers.BasicRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static com.raynigon.unit_api.core.service.UnitsApiService.quantity

@Transactional
@SpringBootTest(
        classes = [
                BasicApplicationConfig,
                BasicEntity,
                BasicRepository
        ],
        properties = [
                "spring.datasource.url=jdbc:tc:postgresql:11:///unitapi",
                "spring.datasource.driverClassName=org.testcontainers.jdbc.ContainerDatabaseDriver",
                "spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQL95Dialect",
                "spring.jpa.hibernate.ddl-auto=create"
        ]
)
class JpaStarterApplicationSpec extends Specification {

    @Autowired
    BasicRepository repository

    def 'context setup works'() {
        expect:
        true
    }

    def 'simple save'() {
        given:
        def entity = new BasicEntity()
        entity.id = "1"
        entity.speed = quantity(10, new KilometrePerHour())
        entity.distance = quantity(10, new Metre())
        entity.temperature = quantity(10, new Celsius())

        when:
        repository.save(entity)

        and:
        def result = repository.getOne(entity.id)

        then:
        result.id == entity.id
        result.speed == entity.speed
        result.distance == entity.distance
        result.temperature == entity.temperature
    }

    def 'simple load'() {
        given:
        // testdata is given in `import.sql`

        when:
        def result = repository.findById("99").orElseThrow()

        then:
        result.id == "99"
        result.speed == quantity(12, new KilometrePerHour())
        result.distance == quantity(100, new Kilometre())
        result.temperature == quantity(30, new Celsius())
    }
}
