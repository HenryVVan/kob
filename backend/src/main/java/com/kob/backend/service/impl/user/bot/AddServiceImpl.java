package com.kob.backend.service.impl.user.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.bot.AddService;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.service.impl.user.bot
 * @Project：backend
 * @Date：2024/1/7 14:58
 * @Filename：AddServiceImpl
 */
@Service
public class AddServiceImpl implements AddService {
    // 注入数据库接口
    @Autowired
    public BotMapper botMapper;

    @Override
    public Map<String, String> add(Map<String, String> data) {
        // 获取授权并获取当前登录的用户
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");

        Map<String, String> map = new HashMap<>();

        if (title == null || title.length() == 0) {
            map.put("error_message", "标题不能为空");
            return map;
        }
        if (title.length() > 100) {
            map.put("error_message", "标题不能超过100个字符");
            return map;
        }
        if (description == null || description.length() == 0) {
            description = "这个用户很神秘，什么都没有留下~";
        }
        if (description.length() > 300) {
            map.put("error_message", "描述不能超过300个字符");
            return map;
        }
        if (content == null || content.length() == 0) {
            map.put("error_message", "代码不能为空");
            return map;
        }
        if (content.length() > 10000) {
            map.put("error_message", "代码不能超过10000个字符");
            return map;
        }

        Date now = new Date();
        Bot bot = new Bot(null, 1, title, description, content, 1500, now, now);
        botMapper.insert(bot);
        map.put("error_message", "success");
        return map;
    }
}
