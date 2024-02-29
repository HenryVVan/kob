package com.kob.matchingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author：Henry Wan
 * @Package：com.kob.matchingsystem.config
 * @Project：backendcloud
 * @Date：2024/2/29 15:57
 * @Filename：RestTemplateConfig
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
