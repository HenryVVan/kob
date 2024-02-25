package com.kob.backend.consumer.utils;

import java.util.Arrays;
import java.util.Random;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.consumer.utils
 * @Project：backend
 * @Date：2024/2/25 20:59
 * @Filename：Game
 */
public class Game {
    final private Integer rows;
    final private Integer cols;

    final private Integer inner_walls_count;
    final private int[][] g;
    // 判断连通的辅助方向数组
    final private static int[] dx = new int[]{-1, 0, 1, 0};
    final private static int[] dy = new int[]{0, -1, 0, 1};

    public Game(Integer rows, Integer cols, Integer inner_walls_count) {
        this.rows = rows;
        this.cols = cols;
        this.inner_walls_count = inner_walls_count;
        this.g = new int[rows][cols];
    }

    public int[][] getG() {
        return g;
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
        return check_connectivity(rows - 2, 1, 1,cols - 2);
    }

    public void createMap() {
        for (int i = 0; i < 1000; i++) {
            if (draw()) break;
        }
    }

}
