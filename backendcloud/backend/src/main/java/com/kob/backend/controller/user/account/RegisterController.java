package com.kob.backend.controller.user.account;

import com.kob.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.controller.user.account
 * @Project：backend
 * @Date：2023/12/25 18:37
 * @Filename：RegisterController
 */

@RestController
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @PostMapping("/user/account/register/")
    public Map<String, String> register(@RequestParam Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        String confirdedPassword = map.get("confirmedPassword");
        return registerService.register(username, password, confirdedPassword);
    }
}