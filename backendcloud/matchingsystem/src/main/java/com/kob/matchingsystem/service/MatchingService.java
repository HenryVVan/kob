package com.kob.matchingsystem.service;

/**
 * @Author：Henry Wan
 * @Package：com.kob.matchingsystem.service
 * @Project：backendcloud
 * @Date：2024/2/28 22:08
 * @Filename：MatchingService
 */
public interface MatchingService {
    String addPlayer(Integer userId, Integer rating, Integer botId);
    String removePlayer(Integer userId);
}
