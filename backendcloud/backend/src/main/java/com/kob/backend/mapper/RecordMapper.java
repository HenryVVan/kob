package com.kob.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kob.backend.pojo.Record;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.mapper
 * @Project：backend
 * @Date：2024/2/28 20:36
 * @Filename：RecordMapper
 */
@Mapper
public interface RecordMapper extends BaseMapper<Record> {
}
