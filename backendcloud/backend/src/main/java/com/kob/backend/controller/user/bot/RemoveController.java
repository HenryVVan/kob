package com.kob.backend.controller.user.bot;

import com.kob.backend.service.bot.RemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.controller.user.bot
 * @Project：backend
 * @Date：2024/1/8 21:41
 * @Filename：RemoveController
 */
@RestController
public class RemoveController {
    @Autowired
    private RemoveService removeService;

    @PostMapping("/api/user/bot/remove/")
    public Map<String, String> remove(@RequestParam Map<String, String> data) {
        return removeService.remove(data);
    }
}
