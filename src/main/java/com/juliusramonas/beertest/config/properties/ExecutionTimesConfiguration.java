package com.juliusramonas.beertest.config.properties;

import com.juliusramonas.beertest.component.advice.MethodExecutionDurations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class ExecutionTimesConfiguration {

    @Bean
    @RequestScope
    public MethodExecutionDurations methodExecutionDurations() {
        return new MethodExecutionDurations();
    }

}
