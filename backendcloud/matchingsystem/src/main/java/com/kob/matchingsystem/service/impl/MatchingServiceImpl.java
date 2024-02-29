package com.kob.matchingsystem.service.impl;

import com.kob.matchingsystem.service.MatchingService;
import com.kob.matchingsystem.service.impl.utils.MatchingPool;
import org.springframework.stereotype.Service;

/**
 * @Author：Henry Wan
 * @Package：com.kob.matchingsystem.service.impl
 * @Project：backendcloud
 * @Date：2024/2/28 22:09
 * @Filename：MatchingServiceImpl
 */
@Service
public class MatchingServiceImpl implements MatchingService {
    // 匹配服务器全局只有一个线程
    public final static MatchingPool matchingPool = new MatchingPool();
    @Override
    public String addPlayer(Integer userId, Integer rating) {
        System.out.println("addPlayer" + " " + userId + " " + rating);
        matchingPool.addPlayer(userId, rating);
        return "addPlayer success";
    }

    @Override
    public String removePlayer(Integer userId) {
        System.out.println("removePlayer" + " " + userId);
        matchingPool.removePlayer(userId);
        return "removePlayer success";
    }
}
