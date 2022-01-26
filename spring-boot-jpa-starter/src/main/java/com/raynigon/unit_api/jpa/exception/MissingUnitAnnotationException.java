package com.raynigon.unit_api.jpa.exception;

import org.jetbrains.annotations.NotNull;

public class MissingUnitAnnotationException extends RuntimeException {

    public MissingUnitAnnotationException(@NotNull String entity, @NotNull String property) {
        super("JpaUnit Annotation is missing in Entity: " + entity + " on Property: " + property);
    }
}
