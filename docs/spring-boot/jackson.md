# Jackson Starter

The Jackson Starter provides a convenient wrapper around the [Unit API Jackson Module](/java/jackson/)

## Project Dependencies

Add the following dependency to your pom.xml/build.gradle file:

=== "Gradle"
    ```groovy 
    implementation("com.raynigon.unit-api:spring-boot-jackson-starter:{{latest_version}}")
    ```
=== "Maven"
    ```xml
    <dependency>
        <groupId>com.raynigon.unit-api</groupId>
        <artifactId>spring-boot-jackson-starter</artifactId>
        <version>{{latest_version}}</version>
    </dependency>
    ```

## Usage
The starter automatically adds the UnitAPI Jackson module to the default ObjectMapper bean.
If the ObjectMapper bean is created by a custom factory, the module has to be added manually.
All instructions are provided [here](/java/jackson/#usage).
The UnitAPI Jackson module itself is also provided as a bean.

## Configuration

The Unit-Api Jackson Starter can be configured with Spring Configuration. The root key is `spring.jackson.unit-api`.

### Options

| Key                                                                | Values      | Description |
| :----------------------------------------------------------------- |:-----------:| ----------: |
| spring.jackson.unit-api.features.SYSTEM_UNIT_ON_MISSING_ANNOTATION | true, false | If no Annotation is present and the given input is a number, the system unit for this quantity should be used. |

### Example

To enable the `SYSTEM_UNIT_ON_MISSING_ANNOTATION` feature add the following line in your `application.properties`:

```
spring.jackson.unit-api.features.SYSTEM_UNIT_ON_MISSING_ANNOTATION=true
```
