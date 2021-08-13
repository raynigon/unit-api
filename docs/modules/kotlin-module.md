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

* Comparison (<,>,<=,>=,==,!=)
* Addition (+)
* Substraction (-)
* Multiplication (*)
* Division (/)

### Examples

=== "Comparison"
    ```kotlin
    val small = Metre(3)
    val large = Metre(4)
    val comp = small < large // true
    ```
=== "Addition"
    ```kotlin
    val x = Metre(3)
    val y = Metre(4)
    val result = x + y // "7m"
    ```
=== "Substraction"
    ```kotlin
    val x = Metre(3)
    val y = Metre(4)
    val result = y - x // "1m"
    ```
=== "Multiplication"
    ```kotlin
    val x = Metre(3)
    val y = Metre(4)
    val result = x * y // "12m²"
    ```
=== "Division"
    ```kotlin
    val x = Metre(8)
    val y = Metre(4)
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

## Geometry
To calculate the length of a hypothenuse in an right-angled triangle you can use the `pythagoreanTheorem` function.
```kotlin
val a: Quantity<Length> = Metre(3)
val b: Quantity<Length> = Metre(4)
val c: Quantity<Length> = pythagoreanTheorem(a, b) // = 5m
```

## Time Quantity
The Kotlin module allows to convert Time Quantities to Java Durations and the other way around.
When the Extension methods are imported, a Time Quantity can be converted to a java duration.
```kotlin
val quantity: Quantity<Time> = Second(10)
val duration: Duration = quantity.toDuration()
```
Its also possible to create a Time Quantity from a java duration.
```kotlin
val duration: Duration = Duration.ofSeconds(10)
val quantity: Quantity<Time> = duration.toQuantity()
```