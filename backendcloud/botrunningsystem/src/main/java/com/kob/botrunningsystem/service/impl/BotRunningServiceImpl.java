package com.kob.botrunningsystem.service.impl;

import com.kob.botrunningsystem.service.BotRunningService;
import com.kob.botrunningsystem.service.impl.utils.BotPool;
import org.springframework.stereotype.Service;

/**
 * @Author：Henry Wan
 * @Package：com.kob.botrunningsystem.service.impl
 * @Project：backendcloud
 * @Date：2024/2/29 22:26
 * @Filename：BotRuningServiceImpl
 */
@Service
public class BotRunningServiceImpl implements BotRunningService {
    public final static BotPool botPool = new BotPool();
    @Override
    public String addBot(Integer userId, String botCode, String input) {
        System.out.println("addBot " + userId + " " + botCode + " " + input);
        botPool.addBot(userId, botCode, input);
        return "addBot success";
    }
}
