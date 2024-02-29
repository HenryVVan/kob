package com.kob.backend.service.impl.user.account;

import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.account.InfoService;
import javafx.util.Pair;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: Henry Wan
 * @Package: com.kob.backend.service.impl.user.account
 * @Project: backend
 * @Date: 2023/12/22 21:47
 * @Filename: InfoServiceImpl
 */
@Service
public class InfoServiceImpl implements InfoService {
    @Override
    public Map<String, String> getInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
            UserDetailsImpl loginInfo = (UserDetailsImpl) authenticationToken.getPrincipal();
            User user = loginInfo.getUser();
            Map<String, String> map = new HashMap<>();
            map.put("error_message", "success");
            map.put("id", user.getId().toString());
            map.put("username", user.getUsername());
            map.put("photo", user.getPhoto());
            return map;
        } else if (authentication instanceof AnonymousAuthenticationToken) {
            // 处理匿名身份的逻辑，例如，返回匿名用户的信息
            Map<String, String> map = new HashMap<>();
            map.put("error_message", "anonymous user");
            return map;
        } else {
            // 处理其他身份类型的逻辑
            Map<String, String> map = new HashMap<>();
            map.put("error_message", "unknown user type");
            return map;
        }
    }
}
