package com.raynigon.unit_api.jackson;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UnitApiJacksonConfiguration {

    @Bean
    UnitApiModule unitApiJacksonModule(){
        return new UnitApiModule();
    }
}