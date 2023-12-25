package com.kob.backend.service.user.account;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.service.user.account
 * @Project：backend
 * @Date：2023/12/22 17:15
 * @Filename：InfoService
 */
public interface InfoService {
    // 接口成员默认是public的
    Map<String, String> getInfo();
}
