package com.raynigon.unit_api.jpa.type;

import com.raynigon.unit_api.core.annotation.QuantityShape;
import com.raynigon.unit_api.core.service.UnitsApiService;
import com.raynigon.unit_api.core.units.si.dimensionless.One;
import com.raynigon.unit_api.jpa.annotation.JpaUnit;
import com.raynigon.unit_api.jpa.annotation.JpaUnitHelper;
import com.raynigon.unit_api.jpa.exception.UnitNotFound;

import java.util.Properties;
import java.util.stream.Stream;
import javax.measure.Quantity;
import javax.measure.Unit;

import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.DoubleTypeDescriptor;
import org.hibernate.usertype.DynamicParameterizedType;

/**
 * Describes Quantity objects for Hibernate
 *
 * @see javax.measure.Quantity
 */
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

    private Unit<?> resolveUnit(JpaUnit jpaUnit, String entity, String property) {
        Class<? extends Quantity<?>> quantityType = JpaUnitHelper.getQuantityType(jpaUnit);
        String unitName = JpaUnitHelper.getUnitName(jpaUnit);
        if (quantityType == null && unitName == null) {
            throw new UnitNotFound("Either QuantityType or unit has to be specified in Entity: " + entity + " on Property: " + property);
        } else if (unitName != null) {
            Unit<?> unit = UnitsApiService.getInstance().getUnit(unitName);
            if (unit == null)
                throw new UnitNotFound("Invalid unit: " + unitName + " in Entity: " + entity + " on Property: " + property);
            return unit;
        } else {
            @SuppressWarnings({"rawtypes", "unchecked"})
            Unit<?> unit = UnitsApiService.getInstance().getUnit((Class) quantityType);
            if (unit == null)
                throw new UnitNotFound("Invalid QuantityType: " + quantityType.getName() + " in Entity: " + entity + " on Property: " + property);
            return unit;
        }
    }

    private JpaUnit getJpaUnitAnnotation(ParameterType reader, String entity, String property) {
        return Stream.of(reader.getAnnotationsMethod())
                .filter(it -> it instanceof JpaUnit)
                .map(it -> ((JpaUnit) it))
                .findFirst()
                .orElseThrow(() -> new UnitNotFound("JpaUnit Annotation is missing in Entity: " + entity + " on Property: " + property));
    }
}
