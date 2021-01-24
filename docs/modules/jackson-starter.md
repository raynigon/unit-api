# Jackson Starter

The Jackson Starter provides a convenient wrapper around the [Jackson Module](/modules/jackson-module)

## Project Dependencies

Add the following dependency to your pom.xml/build.gradle file:

=== "Gradle"
    ```groovy 
    implementation("com.raynigon.unit-api:jackson-starter:{{latest_version}}")
    ```
=== "Maven"
    ```xml
    <dependency>
        <groupId>com.raynigon.unit-api</groupId>
        <artifactId>jackson-starter</artifactId>
        <version>{{latest_version}}</version>
    </dependency>
    ```

## Configuration

The Unit-Api Jackson Starter can be configured with Spring Configuration. The root key is `spring.jackson.unit-api`.

### Options

| Key                                                                | Values      | Description |
| :----------------------------------------------------------------- |:-----------:| ----------: |
| spring.jackson.unit-api.features.SYSTEM_UNIT_ON_MISSING_ANNOTATION | true, false | If no Annotation is present and the given input is a number, the system unit for this quantity should be used. |

### Usage

To enable the `SYSTEM_UNIT_ON_MISSING_ANNOTATION` feature add the following line in your `application.properties`:

```
spring.jackson.unit-api.features.SYSTEM_UNIT_ON_MISSING_ANNOTATION=true
```