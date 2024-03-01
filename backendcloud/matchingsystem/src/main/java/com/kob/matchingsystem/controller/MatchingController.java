package com.kob.matchingsystem.controller;

import com.kob.matchingsystem.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：Henry Wan
 * @Package：com.kob.matchingsystem.controller
 * @Project：backendcloud
 * @Date：2024/2/28 22:12
 * @Filename：MatchingController
 */
@RestController
public class MatchingController {
    @Autowired
    private MatchingService matchingService;

    // 该请求只能由后端服务器向匹配服务器发起，防止攻击者恶意伪造匹配请求
    @PostMapping("/player/add/") // MultiValueMap eg：{1=[1,2,3], 2=[1,2,3]}
    public String addPlayer(@RequestParam MultiValueMap<String, String> data) {
        Integer userId = Integer.parseInt(data.getFirst("userId"));
        Integer rating = Integer.parseInt(data.getFirst("rating"));
        Integer botId = Integer.parseInt(data.getFirst("botId"));
        return matchingService.addPlayer(userId, rating, botId);
    }

    @PostMapping("/player/remove/")
    public String removePlayer(@RequestParam MultiValueMap<String, String> data) {
//        System.out.println("Controller remove start");
        Integer userId = Integer.parseInt(data.getFirst("userId"));
        return matchingService.removePlayer(userId);
    }


}
