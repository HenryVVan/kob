package com.kob.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.pojo
 * @Project：backend
 * @Date：2024/1/7 14:30
 * @Filename：Bot
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bot {
    // 主键自增、数据库和pojo中都需要注明
    @TableId(type = IdType.AUTO)
    private Integer id;
    // pojo里一定要用驼峰命名，不能照抄数据库
    private Integer userId;
    private String title;
    private String description;
    private String content;
    private Integer rating;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifytime;

}
