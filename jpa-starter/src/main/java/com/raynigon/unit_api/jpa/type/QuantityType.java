package com.raynigon.unit_api.jpa.type;

import com.raynigon.unit_api.jpa.annotation.JpaUnit;
import com.raynigon.unit_api.core.service.UnitResolverService;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.DoubleTypeDescriptor;
import org.hibernate.usertype.DynamicParameterizedType;
import tech.units.indriya.AbstractUnit;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class QuantityType extends AbstractSingleColumnStandardBasicType<Quantity<?>> implements DynamicParameterizedType {

    public static final QuantityType INSTANCE = new QuantityType();

    public QuantityType() {
        super(DoubleTypeDescriptor.INSTANCE, new QuantityJavaDescriptor(AbstractUnit.ONE));
    }

    @Override
    public String getName() {
        return QuantityType.class.getSimpleName();
    }

    @Override
    public String[] getRegistrationKeys() {
        return new String[]{
                getName(),
                QuantityType.class.getSimpleName(),
                Quantity.class.getName()
        };
    }

    @Override
    public void setParameterValues(Properties parameters) {
        ParameterType reader = (ParameterType) parameters.get(PARAMETER_TYPE);
        if (reader == null) throw new RuntimeException("Not Implemented");
        Unit<?> resolvedUnit = resolveUnit(reader);
        setJavaTypeDescriptor(new QuantityJavaDescriptor(resolvedUnit));
    }

    private Unit<?> resolveUnit(ParameterType reader) {
        //noinspection unchecked,rawtypes
        Unit<?> typedUnit = UnitResolverService.getInstance().getUnit((Class<Quantity>) reader.getReturnedClass());
        Unit<?> resolvedUnit = Stream.of(reader.getAnnotationsMethod())
                .filter(it -> it instanceof JpaUnit)
                .map(it -> ((JpaUnit) it))
                .map(JpaUnit::unit)
                .filter(Predicate.not(String::isBlank))
                .map(it -> UnitResolverService.getInstance().getUnit(it))
                .filter(Objects::nonNull)
                .findFirst().orElse(null);
        return resolvedUnit != null ? resolvedUnit : typedUnit;
    }
}
