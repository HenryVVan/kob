package com.kob.botrunningsystem.controller;

import com.kob.botrunningsystem.service.BotRunningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：Henry Wan
 * @Package：com.kob.botrunningsystem.controller
 * @Project：backendcloud
 * @Date：2024/2/29 22:28
 * @Filename：BotRunningController
 */
@RestController
public class BotRunningController {
    @Autowired
    private BotRunningService botRunningService;

    @PostMapping("/bot/add/")
    public String addBot(@RequestParam MultiValueMap<String, String> data) {
        Integer userId = Integer.parseInt(data.getFirst("userId"));
        String botCode = data.getFirst("botCode");
        String input = data.getFirst("input");
        return botRunningService.addBot(userId, botCode, input);
    }

}
