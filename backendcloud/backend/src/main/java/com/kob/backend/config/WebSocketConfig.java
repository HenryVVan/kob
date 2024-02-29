package com.kob.backend.config;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.config
 * @Project：backend
 * @Date：2024/2/25 15:25
 * @Filename：WebSocketConfig
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {

        return new ServerEndpointExporter();
    }
}