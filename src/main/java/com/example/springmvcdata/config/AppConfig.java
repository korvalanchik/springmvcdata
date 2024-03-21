package com.example.springmvcdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class AppConfig {
    @Bean
    public DateTimeFormatter customDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("DD-mm-YY");
    }

}
