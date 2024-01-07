package com.kob.backend.service.bot;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.service.bot
 * @Project：backend
 * @Date：2024/1/7 14:47
 * @Filename：updateService
 */

public interface UpdateService {
    Map<String, String> update(Map<String, String> data);
}
