# Quickstart - Jackson

## Project Dependencies
To use the unit-api with Jackson you need to include 
the following dependencies to your pom.xml/build.gradle file:

=== "Gradle"
    ```groovy 
    implementation("com.raynigon.unit-api:jackson-module:{{latest_version}}")
    ```
=== "Maven"
    ```xml
    <dependency>
        <groupId>com.raynigon.unit-api</groupId>
        <artifactId>jackson-module</artifactId>
        <version>{{latest_version}}</version>
    </dependency>
    ```
    
## Usage

You can declare your model like this:
```java
public class BasicEntity {

    public String id;

    @JsonUnit("km/h")
    public Quantity<Speed> speed;
}
```

Jackson will now accept any quantity and convert it to the given unit.
The following examples will be accepted by Jackson:

=== "Number"
    ```json
    {
      "id": "65bf1872-d197-4d72-9950-2b7b4d74a674",
      "speed": 80
    }
    ```
=== "String"
    ```json
    {
      "id": "65bf1872-d197-4d72-9950-2b7b4d74a674",
      "speed": "80"
    }
    ```
=== "String in km/h"
    ```json
    {
      "id": "65bf1872-d197-4d72-9950-2b7b4d74a674",
      "speed": "80 km/h"
    }
    ```
=== "String in m/s"
    ```json
    {
      "id": "65bf1872-d197-4d72-9950-2b7b4d74a674",
      "speed": "22.2222 m/s"
    }
    ```
Every example will result in having the speed property with a value of 80 and the unit "km/h".