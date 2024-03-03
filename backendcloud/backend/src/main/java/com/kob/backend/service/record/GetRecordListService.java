package com.kob.backend.service.record;

import com.alibaba.fastjson2.JSONObject;
import com.kob.backend.pojo.Record;

import java.util.List;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.service.record
 * @Project：backendcloud
 * @Date：2024/3/2 13:13
 * @Filename：GetRecordListService
 */
public interface GetRecordListService {
    JSONObject getList(Integer page); // 返回第几页的分页
}
