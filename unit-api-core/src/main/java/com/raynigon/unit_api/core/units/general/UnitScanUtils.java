package com.raynigon.unit_api.core.units.general;


import com.raynigon.unit_api.core.units.general.IUnit;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("rawtypes")
public class UnitScanUtils {

    public static Set<? extends IUnit<?>> scanForUnits(Package pack) {
        Reflections reflections = new Reflections(pack.getName());
        Set<Class<? extends IUnit>> units = reflections.getSubTypesOf(IUnit.class);
        return units.stream()
                .filter(it -> it.getPackage().getName().contains(pack.getName()))
                .map(UnitScanUtils::createInstanceOrNull)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private static IUnit<?> createInstanceOrNull(Class<? extends IUnit> aClass) {
        Optional<Constructor<?>> result = Arrays.stream(aClass.getConstructors())
                .filter(it -> it.getParameterCount() == 0)
                .findFirst();
        return (IUnit<?>) result.map(it -> {
            try {
                return it.newInstance();
            } catch (Exception e) {
                // TODO Replace with logger
                System.out.println(e);
                return null;
            }
        }).orElse(null);
    }
}