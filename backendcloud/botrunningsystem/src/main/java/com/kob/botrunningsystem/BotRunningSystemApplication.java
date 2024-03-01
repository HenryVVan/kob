package com.kob.botrunningsystem;

import com.kob.botrunningsystem.service.impl.BotRunningServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author：Henry Wan
 * @Package：com.kob.botrunningsystem
 * @Project：Default (Template) Project
 * @Date：2024/2/29 22:14
 * @Filename：${NAME}
 */// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
@SpringBootApplication
public class BotRunningSystemApplication {
    public static void main(String[] args) {
        BotRunningServiceImpl.botPool.start();
        SpringApplication.run(BotRunningSystemApplication.class, args);
    }
}