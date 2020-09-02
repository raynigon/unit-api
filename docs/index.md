# Unit API

![](./logo.png)

## About

The (Raynigon) Unit API provides the integration of the [Unit of Measurement API](https://github.com/unitsofmeasurement) into various frameworks and libraries.
Supported frameworks/libraries are Jackson, Hibernate, Springdoc and Spring Boot.
Spring Boot integrations are available as "-starter" modules. 
This modules will allow you to use the unit-api directly in Spring Boot without configuration.
You can also use the Jackson Module for non Spring boot Use cases.

## Unit of Measurement API

The Unit of Measurement API is mostly unknown, but here is a quick introduction, for more information see [Unit of Measurement API](https://unitsofmeasurement.github.io/unit-api/).

The Unit of Measurement API provides a Java API for handling units and quantities.
Supported methods for unit operations:

* Expression of a quantity in various units
* Checking of unit compatibility
* Arithmetic operations on units

## Maven dependencies

Unit API is distributed as separate JARs with a common version number:

* A core JAR file for core functionality
* A separate JAR file for each of the specialised modules. 
Each module's documentation describes the Maven/Gradle dependency to add to your project's build.

The most commonly used modules are explained in the quick start sections:

* [Spring Boot](/quickstart/spring-boot)
* [Jackson Standalone](/quickstart/jackson)