package com.kob.backend.service.impl.record;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kob.backend.mapper.RecordMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.Record;
import com.kob.backend.pojo.User;
import com.kob.backend.service.record.GetRecordListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.service.impl.record
 * @Project：backendcloud
 * @Date：2024/3/2 13:14
 * @Filename：GetRecordListServiceImpl
 */
@Service
public class GetRecordListServiceImpl implements GetRecordListService {
    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public JSONObject getList(Integer page) {
        // 每一页展示15条记录， 超出范围 返回空
        IPage<Record> recordIPage = new Page<>(page, 9);
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id"); // 按id升序，防止同一时间的两局对局排序错误
        List<Record> records = recordMapper.selectPage(recordIPage, queryWrapper).getRecords();
        List<JSONObject> items = new LinkedList<>();
        JSONObject resp = new JSONObject();
        for (Record record : records) {
            User userA = userMapper.selectById(record.getAId());
            User userB = userMapper.selectById(record.getBId());
            JSONObject item = new JSONObject();
            item.put("a_photo", userA.getPhoto());
            item.put("a_username", userA.getUsername());
            item.put("b_photo", userB.getPhoto());
            item.put("b_username", userB.getUsername());
            String result = "平局"; // 后端判断结果，默认平局
            if ("A".equals(record.getLoser())) result = "B胜";
            else if ("B".equals(record.getLoser())) result = "A胜";
            item.put("result", result);
            item.put("record", record);
            items.add(item);
        }
        resp.put("records", items);
        resp.put("recordsCount", recordMapper.selectCount(null)); // 返回当前传入记录的综述，方便前端显示
//        System.out.println("获取对局记录数据");
        return resp;
    }
}
