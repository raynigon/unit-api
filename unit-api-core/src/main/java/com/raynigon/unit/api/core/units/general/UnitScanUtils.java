package com.raynigon.unit.api.core.units.general;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
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
        Optional<Constructor<?>> result =
                Arrays.stream(aClass.getConstructors())
                        .filter(it -> it.getParameterCount() == 0)
                        .findFirst();
        return (IUnit<?>) result.map(UnitScanUtils::createInstance).orElse(null);
    }

    @Nullable
    private static Object createInstance(Constructor<?> it) {
        try {
            return it.newInstance();
        } catch (Exception e) {
            log.warn("Unable to create Unit Instance for " + it.getName(), e);
            return null;
        }
    }
}
