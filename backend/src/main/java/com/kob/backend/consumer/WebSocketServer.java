package com.kob.backend.consumer;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.consumer
 * @Project：backend
 * @Date：2024/2/22 17:49
 * @Filename：WebSocketServer
 */

import com.alibaba.fastjson2.JSONObject;
import com.kob.backend.consumer.utils.Game;
import com.kob.backend.consumer.utils.JwtAuthentication;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {
    final public static ConcurrentHashMap<Integer, WebSocketServer> userConnectionInfo = new ConcurrentHashMap<>();

    final private static CopyOnWriteArraySet<User> matchPoll = new CopyOnWriteArraySet<>();
    private User user;
    private Session session = null;

    private Game game = null;

    // 因为websocket中并非单例模式，所以需要给其定义成static变量
    private static UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        // 静态变量访问时需要使用类名
        WebSocketServer.userMapper = userMapper;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        this.session = session;
        System.out.println("connected");
        Integer userId = JwtAuthentication.getUserId(token);
        this.user = userMapper.selectById(userId);
        if (this.user != null) {
            userConnectionInfo.put(this.user.getId(), this);
        } else {
            this.session.close();
        }
    }

    @OnClose
    public void onClose() {
        System.out.println("disconnected");
        if (this.user != null) {
            userConnectionInfo.remove(this.user.getId());
            matchPoll.remove(this.user);
        }
    }

    private void startMatching() {
        System.out.println("start matching");
        matchPoll.add(this.user);
        // 后续处理
        while (matchPoll.size() >= 2) {
            Iterator<User> it = matchPoll.iterator();
            User a = it.next(), b = it.next();
            matchPoll.remove(a);
            matchPoll.remove(b);
            Game game = new Game(13, 14, 10, a.getId(), b.getId());
            game.createMap();
            // 将服务器端地图放入到用户连接中
            userConnectionInfo.get(a.getId()).game = game;
            userConnectionInfo.get(b.getId()).game = game;
            game.start();
            // 将与地图相关的信息封装在一起
            JSONObject respGame = new JSONObject();
            respGame.put("a_id", game.getPlayerA().getId());
            respGame.put("a_sx", game.getPlayerA().getSx());
            respGame.put("a_sy",game.getPlayerA().getSy());
            respGame.put("b_id", game.getPlayerB().getId());
            respGame.put("b_sx", game.getPlayerB().getSx());
            respGame.put("b_sy",game.getPlayerB().getSy());
            respGame.put("map", game.getG());

            JSONObject respA = new JSONObject();
            respA.put("event", "start-matching");
            respA.put("opponent_photo", b.getPhoto());
            respA.put("opponent_username", b.getUsername());
            respA.put("game", respGame);
            userConnectionInfo.get(a.getId()).sendMessage(respA.toJSONString());

            JSONObject respB = new JSONObject();
            respB.put("event", "start-matching");
            respB.put("opponent_photo", a.getPhoto());
            respB.put("opponent_username", a.getUsername());
            respB.put("game", respGame);
            userConnectionInfo.get(b.getId()).sendMessage(respB.toJSONString());
        }
    }

    private void stopMatching() {
        System.out.println("stop matching");
        matchPoll.remove(this.user);
    }
    private void move(int direction) {
        if (game.getPlayerA().getId().equals(user.getId())) {
            game.setNextStepA(direction);
        }
        else if (game.getPlayerB().getId().equals(user.getId())) {
            game.setNextStepB(direction);
        }
    }

    @OnMessage
    // 这里的message一般会当成路由
    public void onMessage(String message, Session session) {
        System.out.println("receive message");
        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event");
        if ("start-matching".equals(event)) {
            startMatching();
        } else if ("stop-matching".equals(event)) {
            stopMatching();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    // 后端向前端发送信息
    public void sendMessage(String message) {
        synchronized (this.session) {
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

