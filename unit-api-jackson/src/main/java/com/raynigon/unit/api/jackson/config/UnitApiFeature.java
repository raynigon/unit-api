package com.raynigon.unit.api.jackson.config;

import com.fasterxml.jackson.core.FormatFeature;

public enum UnitApiFeature implements FormatFeature {

    /**
     * If no Annotation is present and no unit was given in the value string,
     * the system unit should be used for the value.
     *
     * E.g. given document {"speed": 10} will be converted to "10 m/s",
     * since the system unit for speed is m/s
     * */
    SYSTEM_UNIT_ON_MISSING_ANNOTATION(false);

    private final boolean _defaultState;
    private final int _mask;

    private UnitApiFeature(boolean defaultState) {
        _defaultState = defaultState;
        _mask = (1 << ordinal());
    }

    @Override
    public boolean enabledByDefault() { return _defaultState; }

    @Override
    public int getMask() { return _mask; }

    @Override
    public boolean enabledIn(int flags) { return (flags & _mask) != 0; }
}
