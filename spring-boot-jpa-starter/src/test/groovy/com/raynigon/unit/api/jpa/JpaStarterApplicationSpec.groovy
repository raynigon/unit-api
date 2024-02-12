package com.raynigon.unit.api.jpa

import com.raynigon.unit.api.jpa.helpers.BasicApplicationConfig
import com.raynigon.unit.api.jpa.helpers.BasicEntity
import com.raynigon.unit.api.jpa.helpers.BasicRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.Celsius
import static com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.Kilometre
import static com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.KilometrePerHour
import static com.raynigon.unit.api.core.units.si.SISystemUnitsConstants.Metre

@Transactional
@SpringBootTest(
        classes = [
                BasicApplicationConfig,
                BasicEntity,
                BasicRepository
        ],
        properties = [
                "spring.datasource.url=jdbc:tc:postgresql:15:///unitapi",
                "spring.datasource.driverClassName=org.testcontainers.jdbc.ContainerDatabaseDriver",
                "spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect",
                "spring.jpa.hibernate.ddl-auto=create",
                // This is needed to import a multi line sql file...
                "spring.jpa.properties.hibernate.hbm2ddl.import_files_sql_extractor=org.hibernate.tool.schema.internal.script.MultiLineSqlScriptExtractor"
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
        entity.speed = KilometrePerHour(10)
        entity.distance = Metre(10)
        entity.temperature = Celsius(10)

        when:
        repository.save(entity)

        and:
        def result = repository.getReferenceById(entity.id)

        then:
        result.id == entity.id
        result.speed == entity.speed
        result.distance == entity.distance
        result.temperature == entity.temperature
    }

    def 'simple load'() {
        given:
        // testdata is given in `import.sql`
        true

        when:
        def result = repository.findById("99").orElseThrow()

        then:
        result.id == "99"
        result.speed == KilometrePerHour(12)
        result.distance == Kilometre(100)
        result.temperature == Celsius(30)
        result.power == null
    }
}
