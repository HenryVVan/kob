package com.kob.backend.service.impl.user.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.bot.UpdateService;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @Date：2024/1/8 22:03
 * @Filename：UpdateServiceImpl
 */
@Service
public class UpdateServiceImpl implements UpdateService {
    @Autowired
    private BotMapper botMapper;


    @Override
    public Map<String, String> update(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authToken.getPrincipal();
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

        Bot bot = botMapper.selectById(data.get("bot_id"));
        if (bot == null) {
            map.put("error_message", "Bot不存在或为空");
            return map;
        }
        if (!bot.getUserId().equals(user.getId())) {
            map.put("error_message", "Bot的用户和当前用户不一致");
            return map;
        }
        Bot new_bot = new Bot(
                bot.getId(),
                user.getId(),
                title,
                description,
                content,
                bot.getRating(),
                bot.getCreatetime(),
                new Date());
        botMapper.updateById(new_bot);
        map.put("error_message", "更新成功");
        return map;
    }
}
