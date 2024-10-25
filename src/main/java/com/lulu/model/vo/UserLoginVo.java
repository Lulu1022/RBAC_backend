package com.lulu.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVo implements Serializable {
    // 使用者ID
    private int userId;

    // 使用者名稱
    private String username;

    // 部門
    private String department;

    // 狀態
    private int status;

    // 角色
    private int role;

    // 角色名稱
    private String roleName;
}
