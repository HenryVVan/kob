package com.kob.botrunningsystem.service.impl.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：Henry Wan
 * @Package：com.kob.botrunningsystem.service.impl
 * @Project：backendcloud
 * @Date：2024/3/1 15:34
 * @Filename：Bot
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bot {
    Integer userId;
    String botCode;
    String input;
}
