# Quickstart - Custom Units

This projects tries to provide all SI Units and many of the common Units.
But sometimes custom units need to be created.
Before you start creating your custom Unit, 
checkout the provided
[units](https://github.com/raynigon/unit-api/tree/master/unit-api-core/src/main/java/com/raynigon/unit_api/core/units)
on Github.

## System of Units
If you want full support for your Units, you have to create a new `SystemOfUnits`.

=== "Kotlin"
    ```kotlin

    package com.company.product.units

    class CustomSystemOfUnits : SystemOfUnits {
    
        companion object {
            const val ID = "CUSTOM_UNIT"
            private val symbolToUnit: Map<String, Unit<*>> = listOf(
                KilometrePer100Hour()
            ).map { it.getSymbol() to it }.toMap()
        }
    
        override fun getName(): String {
            return ID
        }
    
        override fun <Q : Quantity<Q>?> getUnit(quantityType: Class<Q>): Unit<Q>? {
            return units.filterIsInstance<IUnit<Q>>().firstOrNull { quantityType.isAssignableFrom(it.quantityType) }
        }
    
        override fun getUnit(symbol: String): Unit<*>? {
            return symbolToUnit[symbol]
        }
    
        override fun getUnits(): MutableSet<out Unit<*>> {
            return symbolToUnit.values.toMutableSet()
        }
    
        override fun getUnits(dimension: Dimension?): MutableSet<out Unit<*>> {
            return Collections.emptySet()
        }
    }
    ```
=== "Java"
    `Only a Kotlin example is available right now`

As you might have already noticed this SystemOfUnits provides 
a Unit called `KilometrePer100Hour`.

Before we can create this Unit, the SystemOfUnits needs to be registered with the default java service mechanism.
Therefore, we need to create a new file with the path: `META-INF/services/javax.measure.spi.SystemOfUnits` 
which contains the following line:
```plain
com.company.product.units.CustomSystemOfUnits
```

## Custom Unit Definition

Since we have the System of units already registered, we can start developing our custom Unit:

=== "Kotlin"
    ```kotlin
    
    class KilometrePer100Hour :
        TransformedUnit<Speed>(
            "km/100h",
            "KilometrePer100Hour",
            KilometrePerHour().divide(100),
            MetrePerSecond(),
            MultiplyConverter.of(1.0)
        ),
        IUnit<Speed> {

        override fun getSystemId(): String {
            return CustomSystemOfUnits.ID
        }
    
        override fun getQuantityType(): Class<Speed> {
            return Speed::class.java
        }
    
        override fun isSystemUnit(): Boolean {
            return false
        }
    }
    
    @Suppress("FunctionName")
    fun KilometrePer100Hour(value: Number): Quantity<Speed> = quantity(value, KilometrePer100Hour())
    ```
=== "Java"
    `Only a Kotlin example is available right now`

As you can see, this Unit is an expansion of the KilometrePerHour Unit, which is divided by 100.

## Custom Unit Usage

You can use your custom Unit in the same way you would use an existing Unit.

=== "Java"
    `Only a Kotlin example is available right now`
=== "Kotlin"
    ```kotlin

    data class SpeedResponse(
    
        @JsonUnit(KilometrePer100Hour::class)
        val speed: Quantity<Speed>?
    )
    ```