# Java Persistance API (JPA)

## Project Dependencies

Add the following dependency to your pom.xml/build.gradle file:

=== "Gradle"
    ```groovy 
    implementation("com.raynigon.unit-api:spring-boot-jpa-starter:{{latest_version}}")
    ```
=== "Maven"
    ```xml
    <dependency>
        <groupId>com.raynigon.unit-api</groupId>
        <artifactId>spring-boot-jpa-starter</artifactId>
        <version>{{latest_version}}</version>
    </dependency>
    ```

## Limitations
Currently the Raynigon Unit API JPA integration only works for hibernate.

## Usage
To use the Raynigon Unit API with JPA, an Entity needs to be created.
This Entity needs to have a `@TypeDef` annotation with a definition for the `QuantityType`.

!!! warning
    You need to have the `@TypeDef` annotation on every Entity due to a missing feature in hibernate 
    (see [here](https://hibernate.atlassian.net/browse/HHH-11110)).
```java
@Entity
@Table("basic_entity")
@TypeDef(
        name = "quantity",
        typeClass = QuantityType.class,
        defaultForType = Quantity.class
)
public class BasicEntity {

    @Id
    @Column(name="id")
    public String id;
    
    @JpaUnit(KilometrePerHour.class)
    @Column(name="speed")
    public Quantity<Speed> speed;
}
```

Depending on the database you are using, you need to specify the `columnDefinition` property in the `@Column` annotation.
By default, the value stored in the database will be a floating-point value. In this example the value will be saved in "km/h". 