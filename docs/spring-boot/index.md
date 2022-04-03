# Quickstart - Spring Boot

## Project dependencies
To use the unit-api with Spring Boot you need to include 
the following dependencies to your pom.xml/build.gradle file:

=== "Gradle"
    ```groovy 
    implementation("com.raynigon.unit-api:spring-boot-jackson-starter:{{latest_version}}")
    implementation("com.raynigon.unit-api:spring-boot-jpa-starter:{{latest_version}}")
    implementation("com.raynigon.unit-api:spring-boot-springdoc-starter:{{latest_version}}")
    ```
=== "Maven"
    ```xml
    <dependency>
        <groupId>com.raynigon.unit-api</groupId>
        <artifactId>spring-boot-jackson-starter</artifactId>
        <version>{{latest_version}}</version>
    </dependency>
    <dependency>
        <groupId>com.raynigon.unit-api</groupId>
        <artifactId>spring-boot-jpa-starter</artifactId>
        <version>{{latest_version}}</version>
    </dependency>
    <dependency>
        <groupId>com.raynigon.unit-api</groupId>
        <artifactId>spring-boot-springdoc-starter</artifactId>
        <version>{{latest_version}}</version>
    </dependency>
    ```
## Module description

* The first module provides a convenient wrapper around the [Unit API Jackson Module](/java/jackson/).
* The second module provides [JPA integration](/spring-boot/jpa/) for Hibernate.
* The third module provides enhanced API documentation for Quantity and Unit properties in [Springdoc](/spring-boot/springdoc/).