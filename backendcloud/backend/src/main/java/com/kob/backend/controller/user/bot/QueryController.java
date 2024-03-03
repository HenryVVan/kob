package com.kob.backend.controller.user.bot;

import com.kob.backend.pojo.Bot;
import com.kob.backend.service.bot.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.controller.user.bot
 * @Project：backend
 * @Date：2024/1/8 22:24
 * @Filename：QueryController
 */
@RestController
public class QueryController {
    @Autowired
    private QueryService queryService;
    @GetMapping("/api/user/bot/query/")
    public List<Bot> query(){
        return queryService.query();
    }
}
