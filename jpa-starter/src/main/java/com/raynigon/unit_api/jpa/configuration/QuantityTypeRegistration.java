package com.raynigon.unit_api.jpa.configuration;

import com.raynigon.unit_api.jpa.type.QuantityType;
import org.hibernate.boot.model.TypeContributions;
import org.hibernate.boot.model.TypeContributor;
import org.hibernate.service.ServiceRegistry;

public class QuantityTypeRegistration implements TypeContributor {

    @Override
    public void contribute(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        typeContributions.contributeType(QuantityType.INSTANCE);
    }
}