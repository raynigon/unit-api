package com.raynigon.unit_api.jpa.configuration;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.boot.model.TypeContributor;
import org.hibernate.service.ServiceRegistry;

public class QuantityTypeRegistration implements TypeContributor {

  @Override
  public void contribute(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
    // TODO register dynamic type here: typeContributions.contributeType(QuantityType.INSTANCE);
  }
}
