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
 * @Date：2024/2/28 20:31
 * @Filename：Record
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    @TableId(type = IdType.AUTO)
    private Integer id;
    // pojo里一定要用驼峰命名，不能照抄数据库
    private Integer aId;
    private Integer aSx;
    private Integer aSy;
    private Integer bId;
    private Integer bSx;
    private Integer bSy;
    private String aSteps;
    private String bSteps;
    private String map;
    private String loser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
