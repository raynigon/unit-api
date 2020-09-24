package com.raynigon.unit_api.jpa.type;

import com.raynigon.unit_api.core.annotation.QuantityShape;
import com.raynigon.unit_api.core.service.UnitsApiService;
import com.raynigon.unit_api.jpa.exception.QuantityPackingException;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;
import org.hibernate.type.descriptor.spi.JdbcRecommendedSqlTypeMappingContext;
import org.hibernate.type.descriptor.sql.DoubleTypeDescriptor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.util.Objects;

import static com.raynigon.unit_api.core.service.UnitsApiService.quantity;

/**
 * Describes the Quantity Java Class for Hibernate
 *
 * @see javax.measure.Quantity
 */
public class QuantityJavaDescriptor extends AbstractTypeDescriptor<Quantity<?>> {

    private final Unit<?> unit;
    private final QuantityShape shape;

    @SuppressWarnings("unchecked")
    protected QuantityJavaDescriptor(Unit<?> unit, QuantityShape shape) {
        // Double cast is necessary here to trick the compiler,
        // see https://stackoverflow.com/questions/2390662/java-how-do-i-get-a-class-literal-from-a-generic-type
        super((Class<Quantity<?>>) (Class<?>) Quantity.class, new ImmutableMutabilityPlan<>());
        Objects.requireNonNull(unit);
        Objects.requireNonNull(shape);
        this.unit = unit;
        this.shape = shape;
    }

    /**
     * Exposes the value of the unit attribute,
     * used to wrap/unwrap values
     *
     * @return the currently used Unit
     */
    public Unit<?> getUnit() {
        return unit;
    }

    /**
     * Exposes the value of the shape attribute,
     * used in QuantityJavaDescriptor#getJdbcRecommendedSqlType
     *
     * @return the currently used QuantityShape
     */
    public QuantityShape getQuantityShape() {
        return shape;
    }

    @Override
    public String toString(Quantity<?> value) {
        return value.toString();
    }

    @Override
    public Quantity<?> fromString(String string) {
        return quantity(string);
    }

    /**
     * Creates a target object for the given Quantity
     *
     * @param value   the quantity which should be used to create the new object
     * @param type    the type of the new object, only String and Number types are supported
     * @param options unused
     * @throws QuantityPackingException thrown if the type is unknown to the implementation (String and Number types are supported)
     */
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <X> X unwrap(Quantity<?> value, Class<X> type, WrapperOptions options) {
        Quantity<?> convertedValue = value.to((Unit) unit);
        if (Double.class.isAssignableFrom(type)) {
            return (X) Double.valueOf(convertedValue.getValue().doubleValue());
        }
        if (Float.class.isAssignableFrom(type)) {
            return (X) Float.valueOf(convertedValue.getValue().floatValue());
        }
        if (Byte.class.isAssignableFrom(type)) {
            return (X) Byte.valueOf(convertedValue.getValue().byteValue());
        }
        if (Short.class.isAssignableFrom(type)) {
            return (X) Short.valueOf(convertedValue.getValue().shortValue());
        }
        if (Integer.class.isAssignableFrom(type)) {
            return (X) Integer.valueOf(convertedValue.getValue().intValue());
        }
        if (Long.class.isAssignableFrom(type)) {
            return (X) Long.valueOf(convertedValue.getValue().longValue());
        }
        if (String.class.isAssignableFrom(type)) {
            return (X) convertedValue.toString();
        }
        // TODO check for constructor which takes either a string or a number
        throw new QuantityPackingException("Unknown target type: " + type.getName());
    }

    /**
     * Creates a new Quantity object for the given value
     *
     * @param value   the value which should be used to create the new Quantity object, can either be a String or a Number
     * @param options unused
     * @throws QuantityPackingException thrown if the type of the given value is unknown to the implementation (String or Number are accepted types)
     */
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <X> Quantity<?> wrap(X value, WrapperOptions options) {
        if (value instanceof String && ((String) value).contains(" ")) {
            return quantity((String) value).to((Unit) unit);
        } else if (value instanceof String) {
            return quantity(Double.parseDouble((String) value), unit);
        } else if (value instanceof Number) {
            return quantity((Number) value, unit);
        } else {
            throw new QuantityPackingException("Unknown value type: " + value.getClass().getName());
        }
    }

    /**
     * Calculates the best matching sql type by the given QuantityShape
     */
    @Override
    public SqlTypeDescriptor getJdbcRecommendedSqlType(JdbcRecommendedSqlTypeMappingContext context) {
        switch (shape) {
            case STRING:
                return VarcharTypeDescriptor.INSTANCE;
            case NUMBER:
            default:
                return DoubleTypeDescriptor.INSTANCE;
        }
    }
}
