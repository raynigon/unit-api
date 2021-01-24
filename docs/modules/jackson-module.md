# Jackson Module

## Project Dependencies

Add the following dependency to your pom.xml/build.gradle file:

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

## Configuration

### Options
| Feature                           | Default     | Description |
| :-------------------------------- |:-----------:| :---------- |
| SYSTEM_UNIT_ON_MISSING_ANNOTATION |   disabled  | If no Annotation is present and the given input is a number, the system unit for this quantity should be used. |

### Usage

=== "Java"
    ```java
    class JacksonConfiguration {
    
        public ObjectMapper createObjectMapper() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(
                    UnitApiModule.withFeatures(UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION)
            );
            return mapper;
        }
    }
    ```

=== "Kotlin"
    ```kotlin
    class JacksonConfiguration {
    
        fun createObjectMapper(): ObjectMapper {
            val mapper = ObjectMapper()
            mapper.registerModule(
                    UnitApiModule.withFeatures(UnitApiFeature.SYSTEM_UNIT_ON_MISSING_ANNOTATION)
            )
            return mapper
        }
    }
    ```

## Examples
You can declare your model like this:
```java
public class BasicEntity {

    public String id;

    @JsonUnit(KilometrePerHour.class)
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

If you don't want to annotate the Attributes in your class, you can enable the `SYSTEM_UNIT_ON_MISSING_ANNOTATION` Feature.
With this Feature enabled, the Mapper will accept a number and convert it to the given System Unit. (e.g. for speed m/s in the SI-System) 