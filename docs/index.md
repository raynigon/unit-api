# Unit API

![Testcontainers logo](./logo.png)

## About

This Library provides the integration of the unit-api into various frameworks like Jackson, Spring Boot, Hibernate etc.

The Spring Boot starter modules will allow you to use the unit-api directly in Spring Boot without configuration.
You can also use the Jackson Module for non Spring boot Use cases.
The unit-api allows you to specify SI Units instead of primitives,
which will allow compile time verification for all your Unit attributes.

## Maven dependencies

Unit API is distributed as separate JARs with a common version number:

* A core JAR file for core functionality
* A separate JAR file for each of the specialised modules. Each module's documentation describes the Maven/Gradle dependency to add to your project's build.

The most commonly used modules are explained in the quick start sections:

* [Spring Boot](/quickstart/spring-boot)
* [Jackson Standalone](/quickstart/jackson)