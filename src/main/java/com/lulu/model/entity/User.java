package com.lulu.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User{
    private int userId;
    private String username;
    private String password;
    private String email;
    private String department;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int status;

}
