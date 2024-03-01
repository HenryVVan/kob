package com.kob.matchingsystem;

import com.kob.matchingsystem.service.impl.MatchingServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author：Henry Wan
 * @Package：com.kob.matchingsystem
 * @Project：Default (Template) Project
 * @Date：2024/2/28 22:03
 * @Filename：${NAME}
 */// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
@SpringBootApplication
public class MatchingSystemApplication {
    public static void main(String[] args) {
        MatchingServiceImpl.matchingPool.start(); // 启动匹配线程
        SpringApplication.run(MatchingSystemApplication.class, args);
    }
}