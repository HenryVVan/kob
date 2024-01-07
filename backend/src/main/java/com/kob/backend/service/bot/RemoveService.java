package com.kob.backend.service.bot;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.service.bot
 * @Project：backend
 * @Date：2024/1/7 14:47
 * @Filename：removeService
 */

public interface RemoveService {
    Map<String,String> remove(Map<String,String> data);
}
