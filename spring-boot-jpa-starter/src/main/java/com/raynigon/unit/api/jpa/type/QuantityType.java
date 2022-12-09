package com.raynigon.unit.api.jpa.type;

import com.raynigon.unit.api.core.annotation.QuantityShape;
import com.raynigon.unit.api.core.exception.UnitNotFoundException;
import com.raynigon.unit.api.core.service.UnitsApiService;
import com.raynigon.unit.api.core.units.general.IUnit;
import com.raynigon.unit.api.jpa.annotation.JpaUnit;
import com.raynigon.unit.api.jpa.annotation.JpaUnitHelper;
import com.raynigon.unit.api.jpa.cache.CachedQuantity;
import com.raynigon.unit.api.jpa.exception.MissingUnitAnnotationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.SqlTypes;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * Describes Quantity objects for Hibernate
 *
 * @see javax.measure.Quantity
 */
@Slf4j
public class QuantityType implements UserType<Quantity<?>>, DynamicParameterizedType {

    private Unit<?> unit;

    private QuantityShape shape = QuantityShape.NUMBER;

    public @Nullable Unit<?> getUnit() {
        return unit;
    }

    public @NotNull QuantityShape getQuantityShape() {
        return shape;
    }

    @Override
    public void setParameterValues(Properties parameters) {
        String entity = (String) parameters.get(ENTITY);
        String property = (String) parameters.get(PROPERTY);
        ParameterType reader = (ParameterType) parameters.get(PARAMETER_TYPE);
        if (reader == null) throw new NotYetImplementedException();
        JpaUnit jpaUnit = getJpaUnitAnnotation(reader, entity, property);
        unit = resolveUnit(jpaUnit, entity, property);
        shape = JpaUnitHelper.getShape(jpaUnit);
    }

    @Override
    public int getSqlType() {
        return switch (shape) {
            case NUMBER -> SqlTypes.DOUBLE;
            case STRING, NUMERIC_STRING -> SqlTypes.VARCHAR;
            default -> throw new IllegalArgumentException("QuantityShape is not supported: " + shape);
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<Quantity<?>> returnedClass() {
        return (Class<Quantity<?>>) ((Class<?>) Quantity.class);
    }

    @Override
    public boolean equals(Quantity<?> x, Quantity<?> y) {
        return x.equals(y);
    }

    @Override
    public int hashCode(Quantity<?> x) {
        return x.hashCode();
    }


    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Quantity<?> nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        Objects.requireNonNull(unit, "The unit definition is missing");
        if (shape == QuantityShape.NUMBER) {
            return UnitsApiService.quantity(rs.getDouble(position), unit);
        } else if (shape == QuantityShape.NUMERIC_STRING || shape == QuantityShape.STRING) {
            String value = rs.getString(position);
            if (value == null) {
                return null;
            }
            if (!hasUnitSuffix(value)) {
                return UnitsApiService.quantity(Double.parseDouble(value), unit);
            }
            return UnitsApiService.getInstance().parseQuantity(value).to((Unit) unit);
        } else {
            throw new IllegalArgumentException("Unknown Quantity Shape: " + shape);
        }
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void nullSafeSet(PreparedStatement st, Quantity<?> value, int index, SharedSessionContractImplementor session) throws SQLException {
        Objects.requireNonNull(unit, "The unit definition is missing");
        if (value == null) {
            st.setNull(index, Types.DOUBLE);
            return;
        }
        if (shape == QuantityShape.NUMBER) {
            st.setDouble(index, ((Quantity) value).to(unit).getValue().doubleValue());
        } else if (shape == QuantityShape.NUMERIC_STRING) {
            st.setString(index, ((Quantity) value).to(unit).getValue().toString());
        } else if (shape == QuantityShape.STRING) {
            st.setString(index, ((Quantity) value).to(unit).toString());
        } else {
            throw new IllegalArgumentException("Unknown Quantity Shape: " + shape);
        }
    }

    @Override
    public Quantity<?> deepCopy(Quantity<?> value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Serializable disassemble(Quantity<?> value) {
        Objects.requireNonNull(unit, "The unit definition is missing");
        return new CachedQuantity(value.to((Unit) unit).getValue(), unit.getSymbol());
    }

    @Override
    public Quantity<?> assemble(Serializable cached, Object owner) {
        if (!(cached instanceof CachedQuantity source)) {
            throw new IllegalArgumentException("Unexpected cached type: " + cached.getClass());
        }
        Unit<?> unit = UnitsApiService.getInstance().getUnit(source.unit());
        return UnitsApiService.getInstance().createQuantity(source.value(), unit);
    }

    @Override
    public Quantity<?> replace(Quantity<?> detached, Quantity<?> managed, Object owner) {
        return detached;
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    private Unit<?> resolveUnit(JpaUnit jpaUnit, String entity, String property) {
        Class<? extends Quantity<?>> quantityType = JpaUnitHelper.getQuantityType(jpaUnit);
        IUnit<?> unitInstance = JpaUnitHelper.getUnitInstance(jpaUnit);
        if (quantityType == null && unitInstance == null) {
            throw new UnitNotFoundException("Either QuantityType or unit has to be specified in Entity: " + entity + " on Property: " + property);
        } else if (unitInstance != null) {
            return unitInstance;
        } else {
            log.debug("Load System Unit for Quantity {} in Entity {} on Property {}", quantityType, entity, property);
            return UnitsApiService.getInstance().getUnit((Class) quantityType);
        }
    }

    private @NotNull JpaUnit getJpaUnitAnnotation(@NotNull ParameterType reader, @NotNull String entity, @NotNull String property) {
        return Stream.of(reader.getAnnotationsMethod())
                .filter(it -> it instanceof JpaUnit)
                .map(it -> ((JpaUnit) it))
                .findFirst()
                .orElseThrow(() -> new MissingUnitAnnotationException(entity, property));
    }

    private boolean hasUnitSuffix(String value) {
        try {
            Double.parseDouble(value);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }
}
