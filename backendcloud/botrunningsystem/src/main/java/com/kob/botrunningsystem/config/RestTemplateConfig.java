package com.kob.botrunningsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author：Henry Wan
 * @Package：com.kob.botrunningsystem.config
 * @Project：backendcloud
 * @Date：2024/2/29 22:34
 * @Filename：RestTemplateConfig
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
