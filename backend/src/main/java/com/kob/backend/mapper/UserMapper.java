package com.kob.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kob.backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.mapper
 * @Project：backend
 * @Date：2023/12/11 21:24
 * @Filename：UserMapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
