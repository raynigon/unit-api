package com.raynigon.unit.api.jpa.cache;

import java.io.Serializable;

/**
 * The CachedQuantity is a simple Data Transfer Object (DTO)
 * to store a quantity value as number and the unit of the quantity as symbol string.
 * This DTO is needed because the Quantity itself is not serializable.
 *
 * @param value The value of the cached quantity
 * @param unit  The unit of the cached quantity
 */
public record CachedQuantity(
        Number value,
        String unit
) implements Serializable {
}
