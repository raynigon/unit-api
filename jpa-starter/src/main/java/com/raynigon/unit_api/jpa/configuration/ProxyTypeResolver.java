package com.raynigon.unit_api.jpa.configuration;

import com.raynigon.unit_api.jpa.type.QuantityType;
import org.hibernate.MappingException;
import org.hibernate.type.Type;
import org.hibernate.type.TypeFactory;
import org.hibernate.type.TypeResolver;
import org.hibernate.type.spi.TypeConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

public class ProxyTypeResolver extends TypeResolver {

    private TypeFactory typeFactory;
    private Map<String, Class<? extends Type>> typeOverwrites = new HashMap<>();

    public ProxyTypeResolver(TypeConfiguration typeConfiguration, TypeFactory typeFactory) {
        super(typeConfiguration, typeFactory);
        this.typeFactory = typeFactory;
        typeOverwrites.put("javax.measure.Quantity", QuantityType.class);
    }


    public Type heuristicType(String typeName, Properties parameters) throws MappingException {
        if (typeOverwrites.containsKey(typeName) && parameters != null) {
            Class<? extends Type> typeClass = typeOverwrites.get(typeName);
            return typeFactory.byClass(typeClass, parameters);
        }
        return super.heuristicType(typeName, parameters);
    }
}
