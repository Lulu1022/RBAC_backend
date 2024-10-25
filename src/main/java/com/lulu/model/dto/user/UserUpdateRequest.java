package com.lulu.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    // 使用者ID
    private Integer userId;

    // 使用者名稱
    private String username;

    // 密碼，預設為123456
    private String password;

    // 郵箱地址
    private String email;

    // 部門
    private String department;

    // 更新時間
    private Timestamp updatedAt;

    //狀態
    private Integer status;

    //角色
    private Integer role;

}
