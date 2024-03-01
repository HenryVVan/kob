package com.kob.backend.controller.pk;

import com.kob.backend.service.impl.pk.ReceiveBotMoveServiceImpl;
import com.kob.backend.service.pk.ReceiveBotMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.controller.pk
 * @Project：backendcloud
 * @Date：2024/3/1 16:46
 * @Filename：ReceiveBotMoveController
 */
@RestController
public class ReceiveBotMoveController {
    @Autowired
    private ReceiveBotMoveService receiveBotMoveService;

    @PostMapping("/pk/receive/bot/move/")
    public String receiveBotMove(@RequestParam MultiValueMap<String, String> data) {
        Integer userId = Integer.valueOf(data.getFirst("userId"));
        Integer direction = Integer.valueOf(data.getFirst("direction"));
        return receiveBotMoveService.receiveBotMove(userId, direction);
    }
}
