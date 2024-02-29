package com.kob.backend.service.impl.pk;

import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.service.pk.StartGameService;
import org.springframework.stereotype.Service;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.service.impl.pk
 * @Project：backendcloud
 * @Date：2024/2/29 15:44
 * @Filename：StartGameServiceImpl
 */
@Service
public class StartGameServiceImpl implements StartGameService {
    @Override
    public String startGame(Integer aId, Integer bId) {
        System.out.println("start game" + " " + aId + " " + bId);
        WebSocketServer.startGame(aId, bId);
        return "game start success";
    }
}
