package com.github.raynigon.unit_api_starter.jackson;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UnitApiJacksonConfiguration {

    @Bean
    UnitApiModule unitApiJacksonModule(){
        return new UnitApiModule();
    }
}