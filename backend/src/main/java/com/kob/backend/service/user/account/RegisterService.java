package com.kob.backend.service.user.account;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.service.user.account
 * @Project：backend
 * @Date：2023/12/22 17:15
 * @Filename：RegisterService
 */
public interface RegisterService {
    Map<String, String> register(String username, String password, String confimPassword);
}
