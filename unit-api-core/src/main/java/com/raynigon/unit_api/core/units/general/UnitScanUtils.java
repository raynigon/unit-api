package com.raynigon.unit_api.core.units.general;


import org.reflections.Reflections;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@SuppressWarnings("rawtypes")
public class UnitScanUtils {

    private static final Logger logger = Logger.getLogger(UnitScanUtils.class.getName());

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
                logException(e);
                return null;
            }
        }).orElse(null);
    }

    private static void logException(Exception e) {
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final String utf8 = StandardCharsets.UTF_8.name();
            try (PrintStream ps = new PrintStream(baos, true, utf8)) {
                e.printStackTrace(ps);
            }
            String stackTrace = baos.toString(utf8);
            logger.warning(stackTrace);
        } catch (UnsupportedEncodingException uee) {
            logger.severe("UnsupportedEncodingException in " + UnitScanUtils.class.getName());
        }
    }
}