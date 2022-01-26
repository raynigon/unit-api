package com.raynigon.unit_api.springdoc;

import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UnitApiSpringdocConfiguration {

  @Bean
  public PropertyCustomizer unitApiPropertyCustomizer() {
    return new UnitApiPropertyCustomizer();
  }
}
