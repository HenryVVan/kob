package com.kob.backend.consumer.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.expression.spel.ast.Literal;

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
}
