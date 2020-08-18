package com.github.raynigon.unit_api_starter.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
class UnitApiJacksonConfiguration {

    @Bean
    UnitApiJacksonModule unitApiJacksonModule(){
        return new UnitApiJacksonModule();
    }
}