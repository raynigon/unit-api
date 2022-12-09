package com.raynigon.unit.api.jpa.cache;

import java.io.Serializable;

public record CachedQuantity(
        Number value,
        String unit
) implements Serializable {
}
