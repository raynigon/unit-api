# unit-api

![CI](https://github.com/raynigon/unit-api/workflows/CI/badge.svg)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/05af413562694d6ba3b3a923d86da210)](https://app.codacy.com/manual/raynigon/unit-api?utm_source=github.com&utm_medium=referral&utm_content=raynigon/unit-api&utm_campaign=Badge_Grade_Dashboard)
[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/16680694f7a84aab8246e4a7f57b06f3)](https://www.codacy.com/manual/raynigon/unit-api?utm_source=github.com&utm_medium=referral&utm_content=raynigon/unit-api&utm_campaign=Badge_Coverage)

Integration of the junit-api into various frameworks (Jackson, Spring Boot, JPA etc.)

The Spring Boot starter modules will allow you to use the unit-api directly in Spring Boot without configuration.
You can also use the Jackson Module for non Spring boot Use cases.
The unit-api allows you to specify SI Units instead of primitives,
which will allow compile time verification for all your Unit attributes.

## Installation

### Spring Boot
<!-- MODULE_LIST: jackson-starter,jpa-starter -->
**Maven**
```xml
<dependency>
    <groupId>com.raynigon.unit-api</groupId>
    <artifactId>jackson-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>com.raynigon.unit-api</groupId>
    <artifactId>jpa-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

**Gradle**
```groovy
implementation("com.raynigon.unit-api:jackson-starter:0.0.1-SNAPSHOT")
implementation("com.raynigon.unit-api:jpa-starter:0.0.1-SNAPSHOT")
```
<!-- END_MODULE_LIST -->

### Jackson Standalone
<!-- MODULE_LIST: jackson-module -->
**Maven**
```xml
<dependency>
    <groupId>com.raynigon.unit-api</groupId>
    <artifactId>jackson-module</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

**Gradle**
```groovy
implementation("com.raynigon.unit-api:jackson-module:0.0.1-SNAPSHOT")
```
<!-- END_MODULE_LIST -->

## Usage

`TODO`

### Support

The current javadoc can be found [here](https://unit-api.raynigon.com/javadoc/master/).

`TODO`

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

### Roadmap

### Authors

## License
[Apache License 2.0](LICENSE)
