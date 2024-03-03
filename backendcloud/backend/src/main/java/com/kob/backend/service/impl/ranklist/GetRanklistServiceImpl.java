package com.kob.backend.service.impl.ranklist;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.service.ranklist.GetRanklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.service.impl.ranklist
 * @Project：backendcloud
 * @Date：2024/3/3 11:12
 * @Filename：GetRanklistServiceImpl
 */
@Service
public class GetRanklistServiceImpl implements GetRanklistService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public JSONObject getRanklist(Integer page) {
        IPage<User> userIPage = new Page<>(page, 3); // 显示天梯积分前三的用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("rating");
        List<User> list = userMapper.selectPage(userIPage, queryWrapper).getRecords();
        JSONObject resp = new JSONObject();
        // 防止密码泄露，返回时注意清空
        // 此外，使用resttemplate时需要使用multivaluemap，
        // 其他情况返回请求，使用map
        for (User user : list) {
            user.setPassword(null);
        }
        resp.put("users", list);
        resp.put("usersCount", userMapper.selectCount(null));
        return resp;
    }
}
