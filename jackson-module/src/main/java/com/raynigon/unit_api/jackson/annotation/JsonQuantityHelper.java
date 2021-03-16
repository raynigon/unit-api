package com.raynigon.unit_api.jackson.annotation;

import com.raynigon.unit_api.core.io.QuantityReader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class JsonQuantityHelper {

    public static QuantityReader getReaderInstance(JsonQuantityReader readerWrapper) {
        if (readerWrapper == null) return null;
        return createReader(readerWrapper.value());
    }

    private static QuantityReader createReader(Class<? extends QuantityReader> readerType) {
        try {
            Constructor<? extends QuantityReader> ctor = readerType.getConstructor();
            return ctor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Unable to create Quantity Reader " + readerType, e);
        }
    }
}
