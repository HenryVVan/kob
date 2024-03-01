package com.kob.backend.service.impl.pk;

import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.consumer.utils.Game;
import com.kob.backend.service.pk.ReceiveBotMoveService;
import org.springframework.stereotype.Service;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.service.impl.pk
 * @Project：backendcloud
 * @Date：2024/3/1 16:43
 * @Filename：ReceiveBotMoveServiceImpl
 */
@Service
public class ReceiveBotMoveServiceImpl implements ReceiveBotMoveService {
    @Override
    public String receiveBotMove(Integer userId, Integer direction) {
        System.out.println("receive bot move " + userId + " " + direction);
        if (WebSocketServer.userConnectionInfo.get(userId) != null) { // 说明当前机器代码所对应的人正在游戏中
            Game game = WebSocketServer.userConnectionInfo.get(userId).game;
            if (game != null) {
                if (game.getPlayerA().getId().equals(userId)) {
                    game.setNextStepA(direction);
                } else if (game.getPlayerB().getId().equals(userId)) {
                    game.setNextStepB(direction);
                } else {
                    Exception e = new Exception("NoSuchUserError");
                    e.printStackTrace();
                }
            }
        }
        System.out.println("receive bot move going" );
        return "receive bot move success";
    }
}
