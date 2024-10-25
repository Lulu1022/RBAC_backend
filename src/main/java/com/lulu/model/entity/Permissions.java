package com.lulu.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permissions {
    private Integer permissionId;
    private Integer parentId;
    private String tag;
    private String permissionName;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String path;
}
