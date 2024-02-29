package com.kob.matchingsystem.service.impl.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author：Henry Wan
 * @Package：com.kob.matchingsystem.service.impl.utils
 * @Project：backendcloud
 * @Date：2024/2/29 15:01
 * @Filename：MatchingPool
 */
@Component
public class MatchingPool extends Thread {
    private static List<Player> players = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();
    private static RestTemplate restTemplate;
    private final static String startGameUrl = "http://127.0.0.1:6221/pk/start/game/";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        MatchingPool.restTemplate = restTemplate;
    }


    public void addPlayer(Integer userId, Integer rating) {
        lock.lock();
        try {
            players.add(new Player(userId, rating, 0));
        } finally {
            lock.unlock();
        }
    }

    public void removePlayer(Integer userId) {
        lock.lock();
        try {
            List<Player> newPlayers = new ArrayList<>();
            for (Player player : players) {
                if (!player.getUserId().equals(userId)) {
                    newPlayers.add(player);
                }
            }
            players = newPlayers;
        } finally {
            lock.unlock();
        }
    }

    // 将当前所有等待玩家的等待时间+1
    private void increaseWaitingTime() {
        for (Player player : players) {
            player.setWaitingTime(player.getWaitingTime() + 1);
        }
    }

    // 将尝试匹配所有玩家
    private void matchPlayers() {
        System.out.println("match players:" + players.toString());
        boolean[] used = new boolean[players.size()];
        int size = players.size();
        for (int i = 0; i < size; i++) {
            if (used[i]) continue;
            for (int j = i + 1; j < size; j++) {
                if (used[j]) continue;
                Player a = players.get(i);
                Player b = players.get(j);
                if (checkMatching(a, b)) {
                    used[i] = true;
                    used[j] = true;
                    sendResult(a, b);
                    break;
                }
            }
        }
        /*
        为什么不直接将用过的玩家删除
        直接在遍历时从列表中删除元素可能会导致 ConcurrentModificationException 异常
        也可以使用iterator来删除，但是不能处理较复杂逻辑
        */
        List<Player> newPlayers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (!used[i]) {
                newPlayers.add(players.get(i));
            }
        }
        players = newPlayers;
    }

    // 判断两名玩家是否匹配
    private boolean checkMatching(Player a, Player b) {
        int ratingDelta = Math.abs(a.getRating() - b.getRating());
        // 优先匹配最长等待者
        return Math.max(a.getWaitingTime(), b.getWaitingTime()) * 10 >= ratingDelta;
    }

    // 返回玩家A B的匹配结果
    private void sendResult(Player a, Player b) {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("aId", a.getUserId().toString());
        data.add("bId", b.getUserId().toString());
        restTemplate.postForObject(startGameUrl, data, String.class);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                lock.lock();
                try {
                    increaseWaitingTime();
                    matchPlayers();
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
