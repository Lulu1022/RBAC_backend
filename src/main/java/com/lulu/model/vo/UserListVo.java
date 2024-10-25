package com.lulu.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListVo implements Serializable {
    // 使用者ID
    private int userId;

    // 使用者名稱
    private String username;

    // Email
    private String email;

    // 部門
    private String department;

    // 狀態
    private int status;

    // 角色
    private int role;

    // 創建日期
    private Timestamp createdAt;

    //分頁
    private int start;
    private int pageSize;
}
