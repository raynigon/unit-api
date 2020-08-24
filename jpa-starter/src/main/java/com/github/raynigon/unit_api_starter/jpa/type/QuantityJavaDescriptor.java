package com.github.raynigon.unit_api_starter.jpa.type;

import com.github.raynigon.unit_api_starter.jpa.annotation.JpaUnit;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;
import org.hibernate.type.descriptor.spi.JdbcRecommendedSqlTypeMappingContext;
import org.hibernate.type.descriptor.sql.DoubleTypeDescriptor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import tech.units.indriya.quantity.Quantities;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.util.Objects;

public class QuantityJavaDescriptor extends AbstractTypeDescriptor<Quantity<?>> {


    private Unit<?> unit;

    @SuppressWarnings("unchecked")
    protected QuantityJavaDescriptor(Unit<?> unit) {
        // Double cast is necessary here to trick the compiler,
        // see https://stackoverflow.com/questions/2390662/java-how-do-i-get-a-class-literal-from-a-generic-type
        super((Class<Quantity<?>>) (Class<?>) Quantity.class, new ImmutableMutabilityPlan<>());
        Objects.requireNonNull(unit);
        this.unit = unit;
    }

    @Override
    public String toString(Quantity<?> value) {
        return value.toString();
    }

    @Override
    public Quantity<?> fromString(String string) {
        return Quantities.getQuantity(string);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <X> X unwrap(Quantity<?> value, Class<X> type, WrapperOptions options) {
        Quantity<?> convertedValue = value.to((Unit) unit);
        if (type.isAssignableFrom(Double.class)) {
            return (X) Double.valueOf(convertedValue.getValue().doubleValue());
        }
        // TODO handle error
        return null;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <X> Quantity<?> wrap(X value, WrapperOptions options) {
        if (value instanceof String) {
            return Quantities.getQuantity((String) value).to((Unit) unit);
        } else if (value instanceof Number) {
            return Quantities.getQuantity((Number) value, unit);
        } else {
            throw new RuntimeException("Not Implemented"); // TODO handle error
        }
    }

    @Override
    public SqlTypeDescriptor getJdbcRecommendedSqlType(JdbcRecommendedSqlTypeMappingContext context) {
        return DoubleTypeDescriptor.INSTANCE;
    }
}
