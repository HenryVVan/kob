package com.kob.backend.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.controller.user
 * @Project：backend
 * @Date：2023/12/11 21:05
 * @Filename：UserController
 */
@RestController
public class UserController {

    // 使用刚才实现的mapper
    @Autowired
    UserMapper userMapper;

    @GetMapping("/user/all/")
    public List<User> getAll() {
        // 查询所有记录
        return userMapper.selectList(null);
    }

    @GetMapping("/user/{userId}/")
    public User getuser(@PathVariable int userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("id", userId);
        queryWrapper.eq("id", userId);
//        queryWrapper.ge("id", 1).le("id", 3);
        return userMapper.selectOne(queryWrapper);
        // 根据ID查询
//        return userMapper.selectById(userId);

    }

    // 创建新用户，一般应该用post，但为了调试方便，使用get
    @GetMapping("/user/add/{userId}/{username}/{password}/")
    public String addUser(
            @PathVariable int userId,
            @PathVariable String username,
            @PathVariable String password) {
        if (password == null || password.length() < 8) return "密码太短了，请重新输入!";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User(userId, username, passwordEncoder.encode(password));
        userMapper.insert(user);
        return "User add Successfully!";
    }

    @GetMapping("/user/delete/{userId}/")
    public String removeUser(
            @PathVariable int userId
    ) {
        userMapper.deleteById(userId);
        return "Delete User Successfully!";
    }
}

