package com.kob.botrunningsystem.service;

/**
 * @Author：Henry Wan
 * @Package：com.kob.botrunningsystem.service
 * @Project：backendcloud
 * @Date：2024/2/29 22:25
 * @Filename：BotAddService
 */
public interface BotRunningService {
    String addBot(Integer userId, String botCode, String input);
}
