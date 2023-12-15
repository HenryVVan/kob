package com.kob.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.pojo
 * @Project：backend
 * @Date：2023/12/11 21:16
 * @Filename：User
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
}
