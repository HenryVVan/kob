package com.kob.backend.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.service.impl.user.account
 * @Project：backend
 * @Date：2023/12/25 18:20
 * @Filename：RegisterServiceImpl
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> register(String username, String password, String confimPassword) {
        Map<String, String> map = new HashMap<>();
        if (username == null) {
            map.put("error_message", "用户名不能为空");
            return map;
        }
        if (password == null || confimPassword == null) {
            map.put("error_message", "密码不能为空");
            return map;
        }
        username = username.trim();
        if (username.length() == 0) {
            map.put("error_message", "用户名不能为空");
            return map;
        }
        if (password.length() == 0 || confimPassword.length() == 0) {
            map.put("error_message", "密码不能为空");
            return map;
        }
        if (username.length() > 100) {
            map.put("error_message", "用户名长度不能超过100");
            return map;
        }
        if (password.length() > 100 || confimPassword.length() > 100) {
            map.put("error_message", "密码长度不能超过100");
            return map;
        }
        if (!password.equals(confimPassword)) {
            map.put("error_message", "两次密码不一致");
            return map;
        }
        QueryWrapper<User> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<User> userList = userMapper.selectList(queryWrapper);
        if (!userList.isEmpty()) {
            map.put("error_message", "用户名已存在");
            return map;
        }
        String passwordEncode = passwordEncoder.encode(password);
        String photo = "https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231221220557764-16081457.png";
//        因为id是自增的，所以在创建user时可以为空
        User user = new User(null, username, passwordEncode, photo,1500);
        userMapper.insert(user);
        map.put("error_message", "success");
        return map;
    }

}
