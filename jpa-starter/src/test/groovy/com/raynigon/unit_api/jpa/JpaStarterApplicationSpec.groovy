package com.raynigon.unit_api.jpa

import com.raynigon.unit_api.jpa.helpers.BasicApplicationConfig
import com.raynigon.unit_api.jpa.helpers.BasicEntity
import com.raynigon.unit_api.jpa.helpers.BasicRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification
import tech.units.indriya.quantity.Quantities
import tech.units.indriya.unit.Units

import javax.measure.MetricPrefix

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
        entity.speed = Quantities.getQuantity(10, Units.KILOMETRE_PER_HOUR)
        entity.distance = Quantities.getQuantity(10, Units.METRE)
        entity.temperature = Quantities.getQuantity(10, Units.CELSIUS)

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
        result.speed == Quantities.getQuantity(12, Units.KILOMETRE_PER_HOUR)
        result.distance == Quantities.getQuantity(100, Units.METRE.prefix(MetricPrefix.KILO))
        result.temperature == Quantities.getQuantity(30, Units.CELSIUS)
    }
}
