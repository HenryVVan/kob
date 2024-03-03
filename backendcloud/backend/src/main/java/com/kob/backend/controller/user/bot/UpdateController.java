package com.kob.backend.controller.user.bot;

import com.kob.backend.service.bot.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.controller.user.bot
 * @Project：backend
 * @Date：2024/1/8 22:14
 * @Filename：UpdateController
 */
@RestController
public class UpdateController {
    @Autowired
    private UpdateService updateService;

    @PostMapping("/api/user/bot/update/")
    public Map<String, String> update(@RequestParam Map<String, String> data) {
        return updateService.update(data);
    }
}

