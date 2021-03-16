package com.raynigon.unit_api.jackson.annotation;

import com.raynigon.unit_api.core.io.QuantityReader;

public @interface JsonQuantityReader {

    Class<? extends QuantityReader> value();

}
