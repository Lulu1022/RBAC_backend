package com.lulu.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermission {
    private Integer roleId;
    private String roleName;
    private Integer permissionId;
    private Integer parentId;
    private String permissionName;
    private String tag;
}
