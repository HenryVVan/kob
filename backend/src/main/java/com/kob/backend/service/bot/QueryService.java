package com.kob.backend.service.bot;

import com.kob.backend.pojo.Bot;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.service.bot
 * @Project：backend
 * @Date：2024/1/7 14:47
 * @Filename：queryService
 */
// 需要返回一堆bot的信息

public interface QueryService {
    List<Bot> query();
}
