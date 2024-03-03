package com.kob.backend.consumer;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.consumer
 * @Project：backend
 * @Date：2024/2/22 17:49
 * @Filename：WebSocketServer
 */

import com.alibaba.fastjson2.JSONObject;
import com.kob.backend.config.RestTemplateConfig;
import com.kob.backend.consumer.utils.Game;
import com.kob.backend.consumer.utils.JwtAuthentication;
import com.kob.backend.mapper.BotMapper;
import com.kob.backend.mapper.RecordMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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

    private User user;
    private Session session = null;
    public Game game = null;

    // 因为websocket中并非单例模式，所以需要给其定义成static变量
    public static UserMapper userMapper;

    public static RecordMapper recordMapper;
    // 发送请求
    public static RestTemplate restTemplate;

    private static BotMapper botMapper;
    private final static String addPlayerUrl = "http://127.0.0.1:6222/player/add/";
    private final static String removePlayerUrl = "http://127.0.0.1:6222/player/remove/";

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        // 静态变量访问时需要使用类名
        WebSocketServer.userMapper = userMapper;
    }

    @Autowired
    public void setRecordMapper(RecordMapper recordMapper) {
        WebSocketServer.recordMapper = recordMapper;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        WebSocketServer.restTemplate = restTemplate;
    }

    @Autowired
    public void setBotMapper(BotMapper botMapper) {
        WebSocketServer.botMapper = botMapper;
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
        }
    }

    public static void startGame(Integer aId, Integer aBotId, Integer bId, Integer bBotId) {
        User a = userMapper.selectById(aId);
        User b = userMapper.selectById(bId);
        Bot botA = botMapper.selectById(aBotId);
        Bot botB = botMapper.selectById(bBotId);

        // 防止自己匹配自己
        if (a.equals(b)) return;
        Game game = new Game(13,
                14,
                10,
                a.getId(),
                botA,
                b.getId(),
                botB);
        game.createMap();
        // 将服务器端地图放入到用户连接中
        if (userConnectionInfo.get(a.getId()) != null)
            userConnectionInfo.get(a.getId()).game = game;
        // 当某一玩家断开之后，这里a变成null，会报异常
        if (userConnectionInfo.get(b.getId()) != null)
            userConnectionInfo.get(b.getId()).game = game;
        game.start();
        // 将与地图相关的信息封装在一起
        JSONObject respGame = new JSONObject();
        respGame.put("a_id", game.getPlayerA().getId());
        respGame.put("a_sx", game.getPlayerA().getSx());
        respGame.put("a_sy", game.getPlayerA().getSy());
        respGame.put("b_id", game.getPlayerB().getId());
        respGame.put("b_sx", game.getPlayerB().getSx());
        respGame.put("b_sy", game.getPlayerB().getSy());
        respGame.put("map", game.getG());

        JSONObject respA = new JSONObject();
        respA.put("event", "start-matching");
        respA.put("opponent_photo", b.getPhoto());
        respA.put("opponent_username", b.getUsername());
        respA.put("game", respGame);
        if (userConnectionInfo.get(a.getId()) != null)
            userConnectionInfo.get(a.getId()).sendMessage(respA.toJSONString());

        JSONObject respB = new JSONObject();
        respB.put("event", "start-matching");
        respB.put("opponent_photo", a.getPhoto());
        respB.put("opponent_username", a.getUsername());
        respB.put("game", respGame);
        if (userConnectionInfo.get(b.getId()) != null)
            userConnectionInfo.get(b.getId()).sendMessage(respB.toJSONString());
    }

    private void startMatching(Integer botId) {
        System.out.println("start matching");
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("userId", this.user.getId().toString());
        data.add("rating", this.user.getRating().toString());
        data.add("botId", botId.toString());
        // 向Matching System 发请求
        restTemplate.postForObject(addPlayerUrl, data, String.class);

    }

    private void stopMatching() {
        System.out.println("stop matching");
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("userId", this.user.getId().toString());
        restTemplate.postForObject(removePlayerUrl, data, String.class);
    }

    // move时需要注意，人与bot不能同时操作
    private void move(Integer direction) { // 将人的操作对应成数值
        if (game.getPlayerA().getId().equals(user.getId())) {
            if (game.getPlayerA().getBotId().equals(-1)) // 如果是人操作需要屏蔽bot操作
                game.setNextStepA(direction);
        } else if (game.getPlayerB().getId().equals(user.getId())) {
            if (game.getPlayerB().getBotId().equals(-1))
                game.setNextStepB(direction);
        } else {
            Exception e = new Exception("NoSuchUserError");
            e.printStackTrace();
        }
    }

    @OnMessage
    // 这里的message一般会当成路由
    public void onMessage(String message, Session session) {
        System.out.println("Receive message");
        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event");
        if ("start-matching".equals(event)) {
            startMatching(data.getInteger("botId"));
        } else if ("stop-matching".equals(event)) {
            stopMatching();
        } else if ("move".equals(event)) {
            Integer direction = data.getInteger("direction");
            System.out.println(direction);
            move(direction);
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

