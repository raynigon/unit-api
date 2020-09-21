# Kotlin Module
## Project Dependencies
To use the unit-api with Kotlin you can include 
the following dependencies to your pom.xml/build.gradle file:

=== "Gradle"
    ```groovy 
    implementation("com.raynigon.unit-api:unit-api-kotlin:{{latest_version}}")
    ```
=== "Maven"
    ```xml
    <dependency>
        <groupId>com.raynigon.unit-api</groupId>
        <artifactId>unit-api-kotlin</artifactId>
        <version>{{latest_version}}</version>
    </dependency>
    ```
    
## Operators

Kotlin allows custom operators. This Library provides the following operators for the Quantity class:

- Comparison (<,>,<=,>=,==,!=)
- Addition (+)
- Substraction (-)
- Multiplication (*)
- Division (/)

### Examples

=== "Comparison"
    ```kotlin
    val small = Quantities.getQuantity(3, Units.METRE)
    val large = Quantities.getQuantity(4, Units.METRE)
    val comp = small < large // true
    ```
=== "Addition"
    ```kotlin
    val x = Quantities.getQuantity(3, Units.METRE)
    val y = Quantities.getQuantity(4, Units.METRE)
    val result = x + y // "7m"
    ```
=== "Substraction"
    ```kotlin
    val x = Quantities.getQuantity(3, Units.METRE)
    val y = Quantities.getQuantity(4, Units.METRE)
    val result = y - x // "1m"
    ```
=== "Multiplication"
    ```kotlin
    val x = Quantities.getQuantity(3, Units.METRE)
    val y = Quantities.getQuantity(4, Units.METRE)
    val result = x * y // "12m²"
    ```
=== "Division"
    ```kotlin
    val x = Quantities.getQuantity(8, Units.METRE)
    val y = Quantities.getQuantity(4, Units.METRE)
    val result = y / x // "2"
    ```

You can also multiply and divide with factors:

=== "Multiplication"
    ```kotlin
    val x = Quantities.getQuantity(3, Units.METRE)
    val y = 2.0
    val result = x * y // "6m"
    ```
=== "Division"
    ```kotlin
    val x = Quantities.getQuantity(4, Units.METRE)
    val y = 2.0
    val result = x / y // "2m"
    ```