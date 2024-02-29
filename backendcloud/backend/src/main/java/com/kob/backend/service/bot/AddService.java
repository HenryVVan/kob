package com.kob.backend.service.bot;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.service.bot
 * @Project：backend
 * @Date：2024/1/7 14:47
 * @Filename：addService
 */
// 接口上不用标服务，实现上再标
public interface AddService {
    Map<String, String> add(Map<String, String> data);
}
