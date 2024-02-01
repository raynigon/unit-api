package com.raynigon.unit.api.core.units.si;

import static com.raynigon.unit.api.core.service.UnitsApiService.quantity;

import com.raynigon.unit.api.core.quantities.Torque;
import com.raynigon.unit.api.core.units.si.area.SquareMetre;
import com.raynigon.unit.api.core.units.si.mass.Kilogram;
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
import com.raynigon.unit.api.core.units.si.torque.NewtonMetre;
import com.raynigon.unit.api.core.units.si.volume.CubicMetre;
import com.raynigon.unit.api.core.units.si.volume.Litre;
import com.raynigon.unit.api.core.units.si.time.Hour;
import com.raynigon.unit.api.core.units.si.time.Minute;
import com.raynigon.unit.api.core.units.si.time.Second;
import com.raynigon.unit.api.core.units.si.time.MilliSecond;
import com.raynigon.unit.api.core.units.si.time.MicroSecond;
import com.raynigon.unit.api.core.units.si.time.NanoSecond;
import org.jetbrains.annotations.NotNull;

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
    public static final Hour Hour = new Hour();
    public static final Joule Joule = new Joule();
    public static final Kelvin Kelvin = new Kelvin();
    public static final KiloWattHour KiloWattHour = new KiloWattHour();
    public static final Kilogram Kilogram = new Kilogram();
    public static final Kilometre Kilometre = new Kilometre();
    public static final KilometrePerHour KilometrePerHour = new KilometrePerHour();
    public static final Litre Litre = new Litre();
    public static final Metre Metre = new Metre();
    public static final MetrePerSecond MetrePerSecond = new MetrePerSecond();
    public static final MetrePerSquaredSecond MetrePerSquaredSecond = new MetrePerSquaredSecond();
    public static final MicroSecond MicroSecond = new MicroSecond();
    public static final MilliAmpere MilliAmpere = new MilliAmpere();
    public static final MilliSecond MilliSecond = new MilliSecond();
    public static final MilliVolt MilliVolt = new MilliVolt();
    public static final MilliWatt MilliWatt = new MilliWatt();
    public static final MilliWattHour MilliWattHour = new MilliWattHour();
    public static final Millimetre Millimetre = new Millimetre();
    public static final Minute Minute = new Minute();
    public static final NanoSecond NanoSecond = new NanoSecond();
    public static final Newton Newton = new Newton();
    public static final NewtonMetre NewtonMetre = new NewtonMetre();
    public static final One One = new One();
    public static final Percent Percent = new Percent();
    public static final Second Second = new Second();
    public static final SquareMetre SquareMetre = new SquareMetre();
    public static final Volt Volt = new Volt();
    public static final Watt Watt = new Watt();
    public static final WattHour WattHour = new WattHour();

    /*
     * Quantity Factory Methods
     * */
    @SuppressWarnings("PMD.MethodNamingConventions")
    public static Quantity<Acceleration> MetrePerSquaredSecond(Number value) {
        return quantity(value, MetrePerSquaredSecond);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Dimensionless> Number(@NotNull Number value) {
        return quantity(value, One);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Dimensionless> Percent(@NotNull Number value) {
        return quantity(value, Percent);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<ElectricCurrent> MilliAmpere(@NotNull Number value) {
        return quantity(value, MilliAmpere);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<ElectricCurrent> MilliAmperes(@NotNull Number value) {
        return quantity(value, MilliAmpere);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<ElectricCurrent> Ampere(@NotNull Number value) {
        return quantity(value, Ampere);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<ElectricCurrent> Amperes(@NotNull Number value) {
        return quantity(value, Ampere);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<ElectricPotential> MilliVolt(@NotNull Number value) {
        return quantity(value, MilliVolt);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<ElectricPotential> MilliVolts(@NotNull Number value) {
        return quantity(value, MilliVolt);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<ElectricPotential> Volt(@NotNull Number value) {
        return quantity(value, Volt);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<ElectricPotential> Volts(@NotNull Number value) {
        return quantity(value, Volt);
    }


    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<ElectricCharge> AmpereHour(@NotNull Number value) {
        return quantity(value, AmpereHour);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<ElectricCharge> AmpereHours(@NotNull Number value) {
        return quantity(value, AmpereHour);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<ElectricCharge> Coulomb(@NotNull Number value) {
        return quantity(value, Coulomb);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<ElectricCharge> Coulombs(@NotNull Number value) {
        return quantity(value, Coulomb);
    }


    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Energy> Joule(@NotNull Number value) {
        return quantity(value, Joule);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Energy> Joules(@NotNull Number value) {
        return quantity(value, Joule);
    }


    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Energy> KiloWattHour(@NotNull Number value) {
        return quantity(value, KiloWattHour);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Energy> KiloWattHours(@NotNull Number value) {
        return quantity(value, KiloWattHour);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Energy> WattHour(@NotNull Number value) {
        return quantity(value, WattHour);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Energy> WattHours(@NotNull Number value) {
        return quantity(value, WattHour);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Energy> MilliWattHour(@NotNull Number value) {
        return quantity(value, MilliWattHour);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Energy> MilliWattHours(@NotNull Number value) {
        return quantity(value, MilliWattHour);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Force> Newton(@NotNull Number value) {
        return quantity(value, Newton);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Force> Newtons(@NotNull Number value) {
        return quantity(value, Newton);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Torque> NewtonMetre(@NotNull Number value) {
        return quantity(value, NewtonMetre);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Torque> NewtonMetres(@NotNull Number value) {
        return quantity(value, NewtonMetre);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Frequency> Hertz(@NotNull Number value) {
        return quantity(value, Hertz);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Length> Millimetre(@NotNull Number value) {
        return quantity(value, Millimetre);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Length> Millimetres(@NotNull Number value) {
        return quantity(value, Millimetre);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Length> Centimetre(@NotNull Number value) {
        return quantity(value, Centimetre);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Length> Centimetres(@NotNull Number value) {
        return quantity(value, Centimetre);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Length> Metre(@NotNull Number value) {
        return quantity(value, Metre);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Length> Metres(@NotNull Number value) {
        return quantity(value, Metre);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Length> Kilometre(@NotNull Number value) {
        return quantity(value, Kilometre);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Length> Kilometres(@NotNull Number value) {
        return quantity(value, Kilometre);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Mass> Kilogram(@NotNull Number value) {
        return quantity(value, Kilogram);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Mass> Kilograms(@NotNull Number value) {
        return quantity(value, Kilogram);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Power> MilliWatt(@NotNull Number value) {
        return quantity(value, MilliWatt);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Power> MilliWatts(@NotNull Number value) {
        return quantity(value, MilliWatt);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Power> Watt(@NotNull Number value) {
        return quantity(value, Watt);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Power> Watts(@NotNull Number value) {
        return quantity(value, Watt);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Speed> KilometrePerHour(@NotNull Number value) {
        return quantity(value, KilometrePerHour);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Speed> MetrePerSecond(@NotNull Number value) {
        return quantity(value, MetrePerSecond);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Temperature> Celsius(@NotNull Number value) {
        return quantity(value, Celsius);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Temperature> Kelvin(@NotNull Number value) {
        return quantity(value, Kelvin);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Time> Hour(@NotNull Number value) {
        return quantity(value, Hour);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Time> Hours(@NotNull Number value) {
        return quantity(value, Hour);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Time> Minute(@NotNull Number value) {
        return quantity(value, Minute);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Time> Minutes(@NotNull Number value) {
        return quantity(value, Minute);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Time> Second(@NotNull Number value) {
        return quantity(value, Second);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Time> Seconds(@NotNull Number value) {
        return quantity(value, Second);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Time> MilliSecond(@NotNull Number value) {
        return quantity(value, MilliSecond);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Time> MilliSeconds(@NotNull Number value) {
        return quantity(value, MilliSecond);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Time> MicroSecond(@NotNull Number value) {
        return quantity(value, MicroSecond);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Time> MicroSeconds(@NotNull Number value) {
        return quantity(value, MicroSecond);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Time> NanoSecond(@NotNull Number value) {
        return quantity(value, NanoSecond);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Time> NanoSeconds(@NotNull Number value) {
        return quantity(value, NanoSecond);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Area> SquareMetre(@NotNull Number value) {
        return quantity(value, SquareMetre);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Area> SquareMetres(@NotNull Number value) {
        return quantity(value, SquareMetre);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Volume> CubicMetre(@NotNull Number value) {
        return quantity(value, CubicMetre);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Volume> CubicMetres(@NotNull Number value) {
        return quantity(value, CubicMetre);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Volume> Litre(@NotNull Number value) {
        return quantity(value, Litre);
    }

    @SuppressWarnings("PMD.MethodNamingConventions")
    public static @NotNull Quantity<Volume> Litres(@NotNull Number value) {
        return quantity(value, Litre);
    }
}
