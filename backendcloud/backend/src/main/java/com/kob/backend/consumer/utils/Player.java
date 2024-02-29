package com.kob.backend.consumer.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.expression.spel.ast.Literal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.consumer.utils
 * @Project：backend
 * @Date：2024/2/26 18:58
 * @Filename：Player
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private Integer id;
    private Integer sx; // 起始x坐标
    private Integer sy; // 其实y坐标
    private List<Integer> steps; // 存储用户每一步移动的方向

    private boolean checkTailIncreasing(int steps) { //检验当前回合，蛇长度是否会变长
        if (steps <= 10) return true;
        return steps % 3 == 1;
    }

    public List<Cell> getCells() {
        List<Cell> cells = new LinkedList<>();
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};
        int x = sx;
        int y = sy;
        int step = 0;
        for (int d : steps) {
            x += dx[d];
            y += dy[d];
            cells.add(new Cell(x, y));
            // 如果蛇不边长，删除蛇尾
            if (!checkTailIncreasing(++step)) {
                cells.remove(0);
            }
        }
        return cells;
    }

    public String getStepsString() {
        StringBuilder sb = new StringBuilder();
        for (Integer step : steps) {
            sb.append(step).append(",");
        }
        return sb.toString();
    }
}
