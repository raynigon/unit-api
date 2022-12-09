package com.raynigon.unit.api.jackson;

import com.raynigon.unit.api.jackson.config.UnitApiFeature;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfiguration
@EnableConfigurationProperties(UnitApiJacksonProperties.class)
class UnitApiJacksonConfiguration {

  private final UnitApiJacksonProperties properties;

  public UnitApiJacksonConfiguration(UnitApiJacksonProperties properties){
    this.properties = properties;
  }

  @Bean
  public UnitApiModule unitApiJacksonModule() {
    return UnitApiModule.create()
            .enable(properties.getEnabledFeatures().toArray(new UnitApiFeature[0]))
            .disable(properties.getDisabledFeatures().toArray(new UnitApiFeature[0]))
            .build();
  }
}
