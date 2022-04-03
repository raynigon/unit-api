# Unit API - Java

## Quantities

The Raynigon Unit API provides an Implementation of [JSR-385](https://www.jcp.org/en/jsr/detail?id=385).
This allows the usage of [Quantity](http://unitsofmeasurement.github.io/unit-api/site/apidocs/javax/measure/Quantity.html) objects.
A Quantity object consists of a value and a unit in which the value is represented.
The Raynigon Unit API provides static methods to create Quantity objects.

<!--codeinclude-->
[Length Quantity](../../unit-api-core/src/test/groovy/com/raynigon/unit/api/core/units/si/length/MetreSpec.groovy) inside_block:create_metre_quantity
[Temperature Quantity](../../unit-api-core/src/test/groovy/com/raynigon/unit/api/core/units/si/temperature/CelsiusSpec.groovy) inside_block:create_celsius_quantity
<!--/codeinclude-->

## Arithmetic Operations

The arithmetic functionality defined by [JSR-385](https://www.jcp.org/en/jsr/detail?id=385) is also implemented in the Raynigon Unit API.

<!--codeinclude-->
[Temperature Calculation](../../unit-api-core/src/test/groovy/com/raynigon/unit/api/core/units/si/temperature/CelsiusSpec.groovy) inside_block:calculate_with_temperature
<!--/codeinclude-->

## SystemOfUnits

Every Unit used in the Raynigon Unit API has to be associated with exactly one [SystemOfUnits](http://unitsofmeasurement.github.io/unit-api/site/apidocs/javax/measure/spi/SystemOfUnits.html). 

### SISystemOfUnits

The Raynigon Unit API provides the [SISystem](https://unit-api.raynigon.com/javadoc/main/unit-api-core/com/raynigon/unit/api/core/units/si/SISystem.html) as implementation for the SI-Units System as a [SystemOfUnits](http://unitsofmeasurement.github.io/unit-api/site/apidocs/javax/measure/spi/SystemOfUnits.html).
All SI-Units have to be exist in the [SISystem](https://unit-api.raynigon.com/javadoc/main/unit-api-core/com/raynigon/unit/api/core/units/si/SISystem.html) itself. If a SI-Unit is missing in the [SISystem](https://unit-api.raynigon.com/javadoc/main/unit-api-core/com/raynigon/unit/api/core/units/si/SISystem.html), a PR should be created to add it.

### Custom SystemOfUnits
A custom SystemOfUnits can be implemented by implementing the [SystemOfUnits](http://unitsofmeasurement.github.io/unit-api/site/apidocs/javax/measure/spi/SystemOfUnits.html) interface.
The SystemOfUnits hs then to be registered on the [UnitsApiService](https://unit-api.raynigon.com/javadoc/main/unit-api-core/com/raynigon/unit/api/core/service/UnitsApiService.html).
<!--codeinclude-->
[DummySystemOfUnits](../../unit-api-core/src/test/groovy/com/raynigon/unit/api/core/service/testdata/DummySystemOfUnits.groovy) block:DummySystemOfUnits
[DummyUnit](../../unit-api-core/src/test/groovy/com/raynigon/unit/api/core/service/testdata/DummyUnit.groovy) block:DummyUnit
<!--/codeinclude-->

#### Register custom SystemOfUnits in code

The registration can be done by calling the "addSystemOfUnits" method on the [UnitsApiService](https://unit-api.raynigon.com/javadoc/main/unit-api-core/com/raynigon/unit/api/core/service/UnitsApiService.html) or by registering the SystemOfUnits as a service.

<!--codeinclude-->
[UnitsApiService.addSystemOfUnits](../../unit-api-core/src/test/groovy/com/raynigon/unit/api/core/service/UnitsApiServiceSpec.groovy) inside_block:add_system_of_units
<!--/codeinclude-->

#### Register custom SystemOfUnits with SPI
For general information about SPI see [here](https://www.baeldung.com/java-spi).
To register the custom SystemOfUnits with SPI, create a file `javax.measure.spi.SystemOfUnits` in the `META-INF/services` directory.
Add the fully qualified name to the file and ensure the file is present at the specified location in the classpath.
<!--codeinclude-->
[META-INF/services/javax.measure.spi.SystemOfUnits](../../unit-api-core/src/test/resources/META-INF/services/javax.measure.spi.SystemOfUnits) block:
<!--/codeinclude-->