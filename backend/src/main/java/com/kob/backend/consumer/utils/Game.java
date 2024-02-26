package com.kob.backend.consumer.utils;

import com.alibaba.fastjson2.JSONObject;
import com.kob.backend.consumer.WebSocketServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.consumer.utils
 * @Project：backend
 * @Date：2024/2/25 20:59
 * @Filename：Game
 */

public class Game extends Thread {
    private final Integer rows;
    private final Integer cols;

    private final Integer inner_walls_count;
    private final int[][] g;
    // 判断连通的辅助方向数组
    private final static int[] dx = new int[]{-1, 0, 1, 0};
    private final static int[] dy = new int[]{0, -1, 0, 1};
    private final Player playerA, playerB;
    private Integer nextStepA;
    private Integer nextStepB;
    private ReentrantLock lock = new ReentrantLock();

    private String status = "playing"; // playing -> finished
    private String loser = ""; // all:平局 A:B win

    public Game(Integer rows, Integer cols, Integer inner_walls_count, Integer idA, Integer idB) {
        this.rows = rows;
        this.cols = cols;
        this.inner_walls_count = inner_walls_count;
        this.g = new int[rows][cols];
        playerA = new Player(idA, rows - 2, 1, new ArrayList<>());
        playerB = new Player(idB, 1, cols - 2, new ArrayList<>());

    }

    public Player getPlayerA() {
        return playerA;
    }

    public Player getPlayerB() {
        return playerB;
    }

    public int[][] getG() {
        return g;
    }

    public void setNextStepA(Integer nextStepA) {
        lock.lock();
        try {
            this.nextStepA = nextStepA;
        } finally {
            lock.unlock();
        }
    }

    public void setNextStepB(Integer nextStepB) {
        lock.lock();
        try {
            this.nextStepB = nextStepB;
        } finally {
            lock.unlock();
        }
    }

    // 等待两名玩家的下一步操作
    private boolean nextStep() {
        // 由于前端动画每200ms才能画一个格子
        // 如果在此期间接受到的输入 多于一步，会使得中间的移动步被覆盖
        // 因此在每一个下一步前都需要等待200ms
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 如果5s内没有玩家输入，返回false
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
                lock.lock();
                try {
                    if (nextStepA != null && nextStepB != null) {
                        playerA.getSteps().add(nextStepA);
                        playerB.getSteps().add(nextStepB);
                        return true;
                    }
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean check_connectivity(int sx, int sy, int ex, int ey) {
        if (sx == ex && sy == ey) {
            return true;
        }
        g[sx][sy] = 1;
        for (int i = 0; i < 4; i++) {
            int x = sx + dx[i];
            int y = sy + dy[i];
            if (x >= 0 && x < rows && y >= 0 && y < cols && g[x][y] != 1) {
                if (check_connectivity(x, y, ex, ey)) {
                    g[sx][sy] = 0;
                    return true;
                }
            }
        }
        g[sx][sy] = 0;
        return false;
    }

    private boolean draw() {
        for (int i = 0; i < rows; i++) {
            Arrays.fill(g[i], 0);
        }
        // 画墙
        for (int i = 0; i < rows; i++) {
            g[i][0] = g[i][cols - 1] = 1;
        }
        for (int i = 0; i < cols; i++) {
            g[0][i] = g[rows - 1][i] = 1;
        }
        Random rdm = new Random();
        for (int i = 0; i < this.inner_walls_count / 2; i++) {
            for (int j = 0; j < 200; j++) {
                int x = rdm.nextInt(rows);
                int y = rdm.nextInt(cols);
                if (g[x][y] == 1 || g[rows - 1 - x][cols - 1 - y] == 1) continue;
                // 保证起点位置没有墙
                if (x == rows - 2 && y == 1 || x == 1 && y == cols - 2) continue;
                g[x][y] = g[rows - 1 - x][cols - 1 - y] = 1;
                // 成功设置一个障碍物，退出一次循环
                break;
            }
        }
        return check_connectivity(rows - 2, 1, 1, cols - 2);
    }

    public void createMap() {
        for (int i = 0; i < 1000; i++) {
            if (draw()) break;
        }
    }

    // 判断两名玩家下一步操作是否合法
    private void judge(){

    }
    private void sendAllMessage(String message){
        WebSocketServer.userConnectionInfo.get(playerA.getId()).sendMessage(message);
        WebSocketServer.userConnectionInfo.get(playerB.getId()).sendMessage(message);
    }
    // 向两名玩家传递移动信息
    private void sendMove() {
        lock.lock();
        try {
            JSONObject resp = new JSONObject();
            resp.put("event", "move");
            resp.put("a_direction", nextStepA);
            resp.put("b_direction",nextStepB);
            // 下一步时需要清空位置
            nextStepA = nextStepB = null;
            sendAllMessage(resp.toJSONString());
        }
        finally {
            lock.unlock();
        }
    }
    // 向两名玩家传递游戏结果
    private void sendResult(String message){
        JSONObject resp = new JSONObject();
        resp.put("event", "result");
        resp.put("loser", loser);
        sendAllMessage(resp.toJSONString());
    }


    @Override
    public void run() {
        // 保证一定能走完棋盘格子，结束游戏
        for (int i = 0; i < 1000; i++) {
            if (nextStep()) {
                judge();
                if ("playing".equals(status)) {
                    sendMove();
                }
                else {
                    sendResult();
                    break;
                }
            } else {
                this.status = "finished";
                // 在超时的边界读取到了输入，判超时
                lock.lock();
                try {
                    if (nextStepA == null && nextStepB == null) {
                        this.loser = "all";
                    }
                    else if (nextStepA == null) {
                        this.loser = "A";
                    }
                    else {
                        this.loser = "B";
                    }
                }
                finally {
                    lock.unlock();
                }

            }
        }
    }
}
