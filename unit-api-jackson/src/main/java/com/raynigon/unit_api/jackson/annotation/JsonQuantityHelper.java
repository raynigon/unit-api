package com.raynigon.unit_api.jackson.annotation;

import com.raynigon.unit_api.core.io.QuantityReader;
import com.raynigon.unit_api.core.io.QuantityWriter;
import com.raynigon.unit_api.jackson.exception.InitializationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class JsonQuantityHelper {

    public static QuantityReader getReaderInstance(JsonQuantityReader readerWrapper) {
        if (readerWrapper == null) return null;
        return createInstace(readerWrapper.value(), "Reader");
    }

    public static QuantityWriter getWriterInstance(JsonQuantityWriter writerWrapper) {
        if (writerWrapper == null) return null;
        return createInstace(writerWrapper.value(), "Writer");
    }

    private static <T> T createInstace(Class<? extends T> readerType, String type) {
        try {
            Constructor<? extends T> ctor = readerType.getConstructor();
            return ctor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new InitializationException("Unable to create Quantity " + type + ":" + readerType, e);
        }
    }
}
