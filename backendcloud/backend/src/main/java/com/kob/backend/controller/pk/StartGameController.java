package com.kob.backend.controller.pk;

import com.kob.backend.service.pk.StartGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.controller.pk
 * @Project：backendcloud
 * @Date：2024/2/29 15:50
 * @Filename：StartGameController
 */
@RestController
public class StartGameController {
@Autowired
    private StartGameService startGameService;

@PostMapping("/pk/start/game/")
    public String startGame(@RequestParam MultiValueMap<String, String> data) {
        Integer aId = Integer.parseInt(data.getFirst("aId"));
        Integer aBotId = Integer.parseInt(data.getFirst("aBotId"));
        Integer bId = Integer.parseInt(data.getFirst("bId"));
        Integer bBotId = Integer.parseInt(data.getFirst("bBotId"));
        return startGameService.startGame(aId, aBotId, bId, bBotId);
    }
}
