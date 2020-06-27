package ru.burlakov.alfa.task2.config;

import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JooqConfig {

    @Bean
    public DefaultConfiguration configuration() {
        return new DefaultConfiguration();
    }

    @Bean
    public DefaultDSLContext dslContext() {
        return new DefaultDSLContext(configuration());
    }

}
