package com.kob.backend.service.user.account;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.service.user.account
 * @Project：backend
 * @Date：2023/12/22 17:15
 * @Filename：LoginService
 */
public interface LoginService {
    Map<String, String> getToken(String username, String password);
}
