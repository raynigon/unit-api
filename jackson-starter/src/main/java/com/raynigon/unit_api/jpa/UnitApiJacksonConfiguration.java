package com.raynigon.unit_api.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UnitApiJacksonConfiguration {

    @Bean
    UnitApiModule unitApiJacksonModule(){
        return new UnitApiModule();
    }
}