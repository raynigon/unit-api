package com.raynigon.unit.api.core.units.si;

import static com.raynigon.unit.api.core.service.UnitsApiService.quantity;

import com.raynigon.unit.api.core.units.si.area.SquareMetre;
import com.raynigon.unit.api.core.units.si.mass.Kilogram;
import com.raynigon.unit.api.core.units.si.time.*;
import com.raynigon.unit.api.core.units.si.acceleration.MetrePerSquaredSecond;
import com.raynigon.unit.api.core.units.si.dimensionless.One;
import com.raynigon.unit.api.core.units.si.dimensionless.Percent;
import com.raynigon.unit.api.core.units.si.electrical.charge.AmpereHour;
import com.raynigon.unit.api.core.units.si.electrical.charge.Coulomb;
import com.raynigon.unit.api.core.units.si.electrical.current.Ampere;
import com.raynigon.unit.api.core.units.si.electrical.current.MilliAmpere;
import com.raynigon.unit.api.core.units.si.electrical.potential.MilliVolt;
import com.raynigon.unit.api.core.units.si.electrical.potential.Volt;
import com.raynigon.unit.api.core.units.si.energy.Joule;
import com.raynigon.unit.api.core.units.si.energy.KiloWattHour;
import com.raynigon.unit.api.core.units.si.energy.MilliWattHour;
import com.raynigon.unit.api.core.units.si.energy.WattHour;
import com.raynigon.unit.api.core.units.si.force.Newton;
import com.raynigon.unit.api.core.units.si.frequency.Hertz;
import com.raynigon.unit.api.core.units.si.length.Centimetre;
import com.raynigon.unit.api.core.units.si.length.Kilometre;
import com.raynigon.unit.api.core.units.si.length.Metre;
import com.raynigon.unit.api.core.units.si.length.Millimetre;
import com.raynigon.unit.api.core.units.si.power.MilliWatt;
import com.raynigon.unit.api.core.units.si.power.Watt;
import com.raynigon.unit.api.core.units.si.speed.KilometrePerHour;
import com.raynigon.unit.api.core.units.si.speed.MetrePerSecond;
import com.raynigon.unit.api.core.units.si.temperature.Celsius;
import com.raynigon.unit.api.core.units.si.temperature.Kelvin;
import com.raynigon.unit.api.core.units.si.volume.CubicMetre;
import com.raynigon.unit.api.core.units.si.volume.Litre;

import javax.measure.Quantity;
import javax.measure.quantity.Acceleration;
import javax.measure.quantity.Area;
import javax.measure.quantity.Dimensionless;
import javax.measure.quantity.ElectricCharge;
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
import javax.measure.quantity.Volume;

public class SISystemUnitsConstants {

    public static final Ampere Ampere = new Ampere();
    public static final AmpereHour AmpereHour = new AmpereHour();
    public static final Celsius Celsius = new Celsius();
    public static final Centimetre Centimetre = new Centimetre();
    public static final Coulomb Coulomb = new Coulomb();
    public static final CubicMetre CubicMetre = new CubicMetre();
    public static final Hertz Hertz = new Hertz();
    public static final com.raynigon.unit.api.core.units.si.time.Hour Hour = new Hour();
    public static final Joule Joule = new Joule();
    public static final Kelvin Kelvin = new Kelvin();
    public static final KiloWattHour KiloWattHour = new KiloWattHour();
    public static final com.raynigon.unit.api.core.units.si.mass.Kilogram Kilogram = new Kilogram();
    public static final Kilometre Kilometre = new Kilometre();
    public static final KilometrePerHour KilometrePerHour = new KilometrePerHour();
    public static final Litre Litre = new Litre();
    public static final Metre Metre = new Metre();
    public static final MetrePerSecond MetrePerSecond = new MetrePerSecond();
    public static final MetrePerSquaredSecond MetrePerSquaredSecond = new MetrePerSquaredSecond();
    public static final com.raynigon.unit.api.core.units.si.time.MicroSecond MicroSecond = new MicroSecond();
    public static final MilliAmpere MilliAmpere = new MilliAmpere();
    public static final com.raynigon.unit.api.core.units.si.time.MilliSecond MilliSecond = new MilliSecond();
    public static final MilliVolt MilliVolt = new MilliVolt();
    public static final MilliWatt MilliWatt = new MilliWatt();
    public static final MilliWattHour MilliWattHour = new MilliWattHour();
    public static final Millimetre Millimetre = new Millimetre();
    public static final com.raynigon.unit.api.core.units.si.time.Minute Minute = new Minute();
    public static final com.raynigon.unit.api.core.units.si.time.NanoSecond NanoSecond = new NanoSecond();
    public static final Newton Newton = new Newton();
    public static final One One = new One();
    public static final Percent Percent = new Percent();
    public static final Second Second = new Second();
    public static final com.raynigon.unit.api.core.units.si.area.SquareMetre SquareMetre = new SquareMetre();
    public static final Volt Volt = new Volt();
    public static final Watt Watt = new Watt();
    public static final WattHour WattHour = new WattHour();

    /*
     * Quantity Factory Methods
     * */

    public static Quantity<Acceleration> MetrePerSquaredSecond(Number value) {
        return quantity(value, MetrePerSquaredSecond);
    }

    public static Quantity<Dimensionless> Number(Number value) {
        return quantity(value, One);
    }

    public static Quantity<Dimensionless> Percent(Number value) {
        return quantity(value, Percent);
    }

    public static Quantity<ElectricCurrent> MilliAmpere(Number value) {
        return quantity(value, MilliAmpere);
    }

    public static Quantity<ElectricCurrent> Ampere(Number value) {
        return quantity(value, Ampere);
    }

    public static Quantity<ElectricPotential> MilliVolt(Number value) {
        return quantity(value, MilliVolt);
    }

    public static Quantity<ElectricPotential> Volt(Number value) {
        return quantity(value, Volt);
    }

    public static Quantity<ElectricCharge> AmpereHour(Number value) {
        return quantity(value, AmpereHour);
    }

    public static Quantity<ElectricCharge> Coulomb(Number value) {
        return quantity(value, Coulomb);
    }

    public static Quantity<Energy> Joule(Number value) {
        return quantity(value, Joule);
    }

    public static Quantity<Energy> KiloWattHour(Number value) {
        return quantity(value, KiloWattHour);
    }

    public static Quantity<Energy> WattHour(Number value) {
        return quantity(value, WattHour);
    }

    public static Quantity<Energy> MilliWattHour(Number value) {
        return quantity(value, MilliWattHour);
    }

    public static Quantity<Force> Newton(Number value) {
        return quantity(value, Newton);
    }

    public static Quantity<Frequency> Hertz(Number value) {
        return quantity(value, Hertz);
    }

    public static Quantity<Length> Millimetre(Number value) {
        return quantity(value, Millimetre);
    }

    public static Quantity<Length> Centimetre(Number value) {
        return quantity(value, Centimetre);
    }

    public static Quantity<Length> Metre(Number value) {
        return quantity(value, Metre);
    }

    public static Quantity<Length> Kilometre(Number value) {
        return quantity(value, Kilometre);
    }

    public static Quantity<Mass> Kilogram(Number value) {
        return quantity(value, Kilogram);
    }

    public static Quantity<Power> MilliWatt(Number value) {
        return quantity(value, MilliWatt);
    }

    public static Quantity<Power> Watt(Number value) {
        return quantity(value, Watt);
    }

    public static Quantity<Speed> KilometrePerHour(Number value) {
        return quantity(value, KilometrePerHour);
    }

    public static Quantity<Speed> MetrePerSecond(Number value) {
        return quantity(value, MetrePerSecond);
    }

    public static Quantity<Temperature> Celsius(Number value) {
        return quantity(value, Celsius);
    }

    public static Quantity<Temperature> Kelvin(Number value) {
        return quantity(value, Kelvin);
    }

    public static Quantity<Time> Hour(Number value) {
        return quantity(value, Hour);
    }

    public static Quantity<Time> Minute(Number value) {
        return quantity(value, Minute);
    }

    public static Quantity<Time> Second(Number value) {
        return quantity(value, Second);
    }

    public static Quantity<Time> MilliSecond(Number value) {
        return quantity(value, MilliSecond);
    }

    public static Quantity<Time> MicroSecond(Number value) {
        return quantity(value, MicroSecond);
    }

    public static Quantity<Time> NanoSecond(Number value) {
        return quantity(value, NanoSecond);
    }

    public static Quantity<Area> SquareMetre(Number value) {
        return quantity(value, SquareMetre);
    }

    public static Quantity<Volume> CubicMetre(Number value) {
        return quantity(value, CubicMetre);
    }

    public static Quantity<Volume> Litre(Number value) {
        return quantity(value, Litre);
    }

}
