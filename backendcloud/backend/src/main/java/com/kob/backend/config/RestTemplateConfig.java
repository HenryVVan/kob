package com.kob.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.config
 * @Project：backendcloud
 * @Date：2024/2/29 10:55
 * @Filename：RestTemplateConfig
 */
@Configuration
public class RestTemplateConfig {
    // RestTemplate可以在Spirngboot的两个进程间进行通信
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
