package com.demo.spring;

import com.demo.spring.coaches.SwimCoach;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SportConfiguration {
    @Bean
    public Coach swimCoach() {
        return new SwimCoach();
    }
}
