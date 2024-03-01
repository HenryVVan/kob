package com.kob.matchingsystem.service.impl.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：Henry Wan
 * @Package：com.kob.matchingsystem.service.impl.utils
 * @Project：backendcloud
 * @Date：2024/2/29 15:01
 * @Filename：Player
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private Integer userId;
    private Integer rating;
    private Integer botId;
    private Integer waitingTime; // 等待时间
}
