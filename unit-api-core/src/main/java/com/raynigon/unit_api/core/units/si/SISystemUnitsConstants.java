package com.raynigon.unit_api.core.units.si;

import com.raynigon.unit_api.core.units.si.acceleration.MetrePerSquaredSecond;
import com.raynigon.unit_api.core.units.si.dimensionless.One;
import com.raynigon.unit_api.core.units.si.dimensionless.Percent;
import com.raynigon.unit_api.core.units.si.electrical.current.Ampere;
import com.raynigon.unit_api.core.units.si.electrical.potential.Volt;
import com.raynigon.unit_api.core.units.si.energy.Joule;
import com.raynigon.unit_api.core.units.si.energy.KiloWattHour;
import com.raynigon.unit_api.core.units.si.energy.MilliWattHour;
import com.raynigon.unit_api.core.units.si.energy.WattHour;
import com.raynigon.unit_api.core.units.si.force.Newton;
import com.raynigon.unit_api.core.units.si.frequency.Hertz;
import com.raynigon.unit_api.core.units.si.length.Centimetre;
import com.raynigon.unit_api.core.units.si.length.Kilometre;
import com.raynigon.unit_api.core.units.si.length.Metre;
import com.raynigon.unit_api.core.units.si.length.Millimetre;
import com.raynigon.unit_api.core.units.si.mass.Kilogram;
import com.raynigon.unit_api.core.units.si.power.MilliWatt;
import com.raynigon.unit_api.core.units.si.power.Watt;
import com.raynigon.unit_api.core.units.si.speed.KilometrePerHour;
import com.raynigon.unit_api.core.units.si.speed.MetrePerSecond;
import com.raynigon.unit_api.core.units.si.temperature.Celsius;
import com.raynigon.unit_api.core.units.si.temperature.Kelvin;
import com.raynigon.unit_api.core.units.si.time.Hour;
import com.raynigon.unit_api.core.units.si.time.MicroSecond;
import com.raynigon.unit_api.core.units.si.time.MilliSecond;
import com.raynigon.unit_api.core.units.si.time.Minute;
import com.raynigon.unit_api.core.units.si.time.Second;

import javax.measure.Quantity;
import javax.measure.quantity.Acceleration;
import javax.measure.quantity.Dimensionless;
import javax.measure.quantity.ElectricCurrent;
import javax.measure.quantity.ElectricPotential;
import javax.measure.quantity.Energy;
import javax.measure.quantity.Force;
import javax.measure.quantity.Frequency;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Power;
import javax.measure.quantity.Speed;
import javax.measure.quantity.Temperature;
import javax.measure.quantity.Time;

import static com.raynigon.unit_api.core.service.UnitsApiService.quantity;

public class SISystemUnitsConstants {

    public static Quantity<Acceleration> MetrePerSquaredSecond(Number value){
        return quantity(value, new MetrePerSquaredSecond());
    }

    public static Quantity<Dimensionless> Number(Number value){
        return quantity(value, new One());
    }

    public static Quantity<Dimensionless> Percent(Number value){
        return quantity(value, new Percent());
    }

    public static Quantity<ElectricCurrent> Ampere(Number value){
        return quantity(value, new Ampere());
    }

    public static Quantity<ElectricPotential> Volt(Number value){
        return quantity(value, new Volt());
    }

    public static Quantity<Energy> Joule(Number value){
        return quantity(value, new Joule());
    }

    public static Quantity<Energy> KiloWattHour(Number value){
        return quantity(value, new KiloWattHour());
    }

    public static Quantity<Energy> WattHour(Number value){
        return quantity(value, new WattHour());
    }

    public static Quantity<Energy> MilliWattHour(Number value){
        return quantity(value, new MilliWattHour());
    }

    public static Quantity<Force> Newton(Number value){
        return quantity(value, new Newton());
    }

    public static Quantity<Frequency> Hertz(Number value){
        return quantity(value, new Hertz());
    }

    public static Quantity<Length> Millimetre(Number value){
        return quantity(value, new Millimetre());
    }

    public static Quantity<Length> Centimetre(Number value){
        return quantity(value, new Centimetre());
    }

    public static Quantity<Length> Metre(Number value){
        return quantity(value, new Metre());
    }

    public static Quantity<Length> Kilometre(Number value){
        return quantity(value, new Kilometre());
    }

    public static Quantity<Mass> Kilogram(Number value){
        return quantity(value, new Kilogram());
    }

    public static Quantity<Power> MilliWatt(Number value){
        return quantity(value, new MilliWatt());
    }

    public static Quantity<Power> Watt(Number value){
        return quantity(value, new Watt());
    }

    public static Quantity<Speed> KilometrePerHour(Number value){
        return quantity(value, new KilometrePerHour());
    }

    public static Quantity<Speed> MetrePerSecond(Number value){
        return quantity(value, new MetrePerSecond());
    }

    public static Quantity<Temperature> Celsius(Number value){
        return quantity(value, new Celsius());
    }

    public static Quantity<Temperature> Kelvin(Number value){
        return quantity(value, new Kelvin());
    }

    public static Quantity<Time> Hour(Number value){
        return quantity(value, new Hour());
    }

    public static Quantity<Time> Minute(Number value){
        return quantity(value, new Minute());
    }

    public static Quantity<Time> Second(Number value){
        return quantity(value, new Second());
    }

    public static Quantity<Time> MilliSecond(Number value){
        return quantity(value, new MilliSecond());
    }

    public static Quantity<Time> MicroSecond(Number value){
        return quantity(value, new MicroSecond());
    }
}
