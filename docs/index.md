# Unit API

![](./logo.png)

## About

The (Raynigon) Unit API provides an Implementation of [JSR-385](https://www.jcp.org/en/jsr/detail?id=385) 
with focus on the SI System redefinition, modularity and support for Java SE 8/9 and above.
It integrates into various Frameworks, Libraries and Languages, such as Spring Boot, Hibernate, Jackson, Kotlin and more.  
Spring Boot integrations are available as "-starter" modules. 
The Spring Boot Starter modules will allow you to use the unit-api directly in Spring Boot without configuration.
You can also use the Jackson Module, Kotlin Module for non Spring boot Use cases.

## Unit of Measurement API

The majority of the code in this library originates from the [Unit of Measurement API](https://unitsofmeasurement.github.io/unit-api/).
The new code enables better integration into Frameworks, Libraries and Languages.

## Maven dependencies

Unit API is distributed as separate JARs with a common version number:

* A core JAR file for core functionality
* A separate JAR file for each of the specialised modules. 
Each module's documentation describes the Maven/Gradle dependency to add to your project's build.

The most commonly used modules are explained in the quick start sections:

* [Spring Boot](/quickstart/spring-boot)
* [Jackson Standalone](/quickstart/jackson)

## Kotlin Support

There is a module which adds support for Kotlin operators:

* [Kotlin](/modules/kotlin-module)