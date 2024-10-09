package com.notifyMeSchedular.notifyMeSchedular.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class AppConfig {

    //This returns this restTemplate() to be used by any component or other type of beans.
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
