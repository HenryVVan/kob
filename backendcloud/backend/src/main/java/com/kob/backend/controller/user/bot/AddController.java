package com.kob.backend.controller.user.bot;

import com.kob.backend.service.bot.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.controller.user.bot
 * @Project：backend
 * @Date：2024/1/7 16:33
 * @Filename：AddController
 */
@RestController
public class AddController {
    @Autowired
    private AddService addService;

    @PostMapping("/user/bot/add/")
    public Map<String, String> add(@RequestParam Map<String, String> data) {
        return addService.add(data);
    }
}
