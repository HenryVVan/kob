package com.kob.backend.controller.user.account;

import com.kob.backend.service.user.account.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.controller.user.account
 * @Project：backend
 * @Date：2023/12/22 17:04
 * @Filename：LoginController
 */
@RestController
public class LoginController {
    // 将user中对应的接口注入, 实现对应方法
    @Autowired
    private LoginService loginService;

    @PostMapping("/user/account/token/")
    public Map<String, String> getToken(@RequestParam Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        System.out.println(username + " " + password);
        return loginService.getToken(username, password);
    }

}