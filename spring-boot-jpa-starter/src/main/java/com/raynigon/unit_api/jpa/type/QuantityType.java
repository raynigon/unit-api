package com.raynigon.unit_api.jpa.type;

import com.raynigon.unit_api.core.annotation.QuantityShape;
import com.raynigon.unit_api.core.exception.UnitNotFoundException;
import com.raynigon.unit_api.core.service.UnitsApiService;
import com.raynigon.unit_api.core.units.general.IUnit;
import com.raynigon.unit_api.core.units.si.dimensionless.One;
import com.raynigon.unit_api.jpa.annotation.JpaUnit;
import com.raynigon.unit_api.jpa.annotation.JpaUnitHelper;

import java.util.Properties;
import java.util.stream.Stream;
import javax.measure.Quantity;
import javax.measure.Unit;

import com.raynigon.unit_api.jpa.exception.MissingUnitAnnotationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.DoubleTypeDescriptor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.jetbrains.annotations.NotNull;

/**
 * Describes Quantity objects for Hibernate
 *
 * @see javax.measure.Quantity
 */
@Slf4j
public class QuantityType extends AbstractSingleColumnStandardBasicType<Quantity<?>>
        implements DynamicParameterizedType {

    public static final QuantityType INSTANCE = new QuantityType();

    public QuantityType() {
        super(
                DoubleTypeDescriptor.INSTANCE, new QuantityJavaDescriptor(new One(), QuantityShape.NUMBER));
    }

    @Override
    public String getName() {
        return QuantityType.class.getSimpleName();
    }

    @Override
    public String[] getRegistrationKeys() {
        return new String[]{QuantityType.class.getSimpleName(), Quantity.class.getName()};
    }

    @Override
    public void setParameterValues(Properties parameters) {
        String entity = (String) parameters.get(ENTITY);
        String property = (String) parameters.get(PROPERTY);
        ParameterType reader = (ParameterType) parameters.get(PARAMETER_TYPE);
        if (reader == null) throw new NotYetImplementedException();
        JpaUnit jpaUnit = getJpaUnitAnnotation(reader, entity, property);
        Unit<?> resolvedUnit = resolveUnit(jpaUnit, entity, property);
        QuantityShape shape = JpaUnitHelper.getShape(jpaUnit);
        setJavaTypeDescriptor(new QuantityJavaDescriptor(resolvedUnit, shape));
    }

    @SuppressWarnings("unchecked")
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
}
