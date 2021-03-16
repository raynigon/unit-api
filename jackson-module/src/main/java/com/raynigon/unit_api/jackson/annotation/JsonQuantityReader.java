package com.raynigon.unit_api.jackson.annotation;

import com.raynigon.unit_api.core.io.QuantityReader;
import com.raynigon.unit_api.core.units.general.IUnit;

public @interface JsonQuantityReader {

    Class<? extends QuantityReader> value();

}