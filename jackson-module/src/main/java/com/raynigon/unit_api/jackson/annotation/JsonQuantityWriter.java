package com.raynigon.unit_api.jackson.annotation;

import com.raynigon.unit_api.core.io.QuantityReader;
import com.raynigon.unit_api.core.io.QuantityWriter;

public @interface JsonQuantityWriter {

    Class<? extends QuantityWriter> value();
}
