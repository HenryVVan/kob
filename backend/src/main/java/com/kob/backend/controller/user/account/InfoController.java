package com.kob.backend.controller.user.account;

import com.kob.backend.service.user.account.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.controller.user.account
 * @Project：backend
 * @Date：2023/12/22 21:52
 * @Filename：InfoServiceImpl
 */
@RestController
public class InfoServiceImpl {
    @Autowired
    private InfoService infoService;

    @GetMapping("/user/account/info/")
    public Map<String, String> getInfo() {
        return infoService.getInfo();
    }
}
