package com.kob.botrunningsystem.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

public class BotImpl implements Supplier<Integer> {
    @Override
    public Integer get() {
        File file = new File("input.txt");
        try {
            Scanner scanner = new Scanner(file);
            return nextMove(scanner.next());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Cell {
        public final int x, y;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    private boolean checkTailIncreasing(int steps) { //检验当前回合，蛇长度是否会变长
        if (steps <= 10) return true;
        return steps % 3 == 1;
    }

    public List<Cell> getCells(int sx, int sy, String steps) {
        // 注意编码的时候给路径前后加了()
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};
        steps = steps.substring(1, steps.length() - 1);
//        System.out.println(Arrays.toString(steps));
        List<Cell> cells = new LinkedList<>();
        int x = sx;
        int y = sy;
        int step = 0;
        cells.add(new Cell(x, y));
        for (char d : steps.toCharArray()) {
            if (d >= '0' && d <= '3') { // 只处理代表方向的字符
                int dir = d - '0';
                x += dx[dir];
                y += dy[dir];
                cells.add(new Cell(x, y));
                // 如果蛇不变长，删除蛇尾
                if (!checkTailIncreasing(++step)) {
                    cells.remove(0);
                }
            }
        }
        return cells;
    }


//    public Integer nextMove(String input) {
//        String[] strs = input.split("#");
//        String map = strs[0];
//        int[][] g = new int[13][14];
//        // 获取地图
//        for (int i = 0, k = 0; i < 13; i++) {
//            for (int j = 0; j < 14; j++, k++) {
//                if (map.charAt(k) == '1') {
//                    g[i][j] = 1;
//                }
//            }
//        }
//        Integer aSx = Integer.parseInt(strs[1]), aSy = Integer.parseInt(strs[2]);
//        Integer bSx = Integer.parseInt(strs[4]), bSy = Integer.parseInt(strs[5]);
//        // 取出蛇的轨迹
//        List<Cell> aSnake = getCells(aSx, aSy, strs[3]);
//        List<Cell> bSnake = getCells(bSx, bSy, strs[6]);
//        for (Cell c : aSnake) g[c.x][c.y] = 1;
//        for (Cell c : bSnake) g[c.x][c.y] = 1;
//        // 判断可行的移动方向
//        // 对于四种方向0(↑), 1(→), 2(↓), 3(←)
//        // 在行和列方向上的计算偏移量
//        int[] dx = {-1, 0, 1, 0};
//        int[] dy = {0, 1, 0, -1};
//        for (int i = 0; i < 4; i++) {
//            int x = aSnake.get(aSnake.size() - 1).x + dx[i];
//            int y = aSnake.get(aSnake.size() - 1).y + dy[i];
//            if (x >= 0 && x < 13 && y >= 0 && y < 14 && g[x][y] == 0) {
//                return i;
//            }
//        }
//        return 0;
//    }

    public Integer nextMove(String input) {
        String[] strs = input.split("#");
        String map = strs[0];
        int[][] g = new int[13][14];
        // 获取地图
        for (int i = 0, k = 0; i < 13; i++) {
            for (int j = 0; j < 14; j++, k++) {
                if (map.charAt(k) == '1') {
                    g[i][j] = 1;
                }
            }
        }
        Integer aSx = Integer.parseInt(strs[1]), aSy = Integer.parseInt(strs[2]);
        Integer bSx = Integer.parseInt(strs[4]), bSy = Integer.parseInt(strs[5]);
        List<Cell> aSnake = getCells(aSx, aSy, strs[3]);
        List<Cell> bSnake = getCells(bSx, bSy, strs[6]);
        for (Cell c : aSnake) g[c.x][c.y] = 1;
        for (Cell c : bSnake) g[c.x][c.y] = 1;

        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};
        int bestMove = 0;
        int maxSafety = -1;
        for (int i = 0; i < 4; i++) {
            int x = aSnake.get(aSnake.size() - 1).x + dx[i];
            int y = aSnake.get(aSnake.size() - 1).y + dy[i];
            if (x >= 0 && x < 13 && y >= 0 && y < 14 && g[x][y] == 0) {
                // 模拟下一步后的情况
                int safety = 0; // 定义一个安全度变量，用于评估此移动的安全性
                for (int j = 0; j < 4; j++) {
                    int nx = x + dx[j];
                    int ny = y + dy[j];
                    if (nx >= 0 && nx < 13 && ny >= 0 && ny < 14 && g[nx][ny] == 0) {
                        safety++; // 如果下一步之后的位置是安全的，则增加安全度
                    }
                }
                // 选择安全度最高的移动
                if (safety > maxSafety) {
                    maxSafety = safety;
                    bestMove = i;
                }
            }
        }
        return bestMove;
    }

}
