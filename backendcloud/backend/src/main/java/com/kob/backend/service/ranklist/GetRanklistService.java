package com.kob.backend.service.ranklist;

import com.alibaba.fastjson2.JSONObject;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.service.ranklist
 * @Project：backendcloud
 * @Date：2024/3/3 11:11
 * @Filename：GetRanklistService
 */
public interface GetRanklistService {
    JSONObject getRanklist(Integer page);
}
