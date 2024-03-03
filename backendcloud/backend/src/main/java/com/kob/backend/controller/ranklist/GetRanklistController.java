package com.kob.backend.controller.ranklist;

import com.alibaba.fastjson2.JSONObject;
import com.kob.backend.service.ranklist.GetRanklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.controller.ranklist
 * @Project：backendcloud
 * @Date：2024/3/3 11:13
 * @Filename：GetRanklistControoler
 */
@RestController
public class GetRanklistController {
    @Autowired
    private GetRanklistService getRanklistService;

    @GetMapping("/api/ranklist/getlist/")
    public JSONObject getRanklist(@RequestParam Map<String, String> data) {
        Integer page = Integer.parseInt(data.get("page"));
        return getRanklistService.getRanklist(page);
    }

}
