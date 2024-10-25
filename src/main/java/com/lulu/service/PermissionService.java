package com.lulu.service;

import com.lulu.model.entity.Permissions;
import com.lulu.model.entity.Tree;

import java.util.List;

public interface PermissionService {
    void savePermission();
    Permissions findPermissionById(int id);
    List<Permissions> findAll();

    void createPermission(Permissions permission);

    void updatePermission(Permissions permission);

    void deletePermission(int id);

    List<Permissions> findPermissionsByUserId(int userId);
    List<String> findPermissionsPathByUserId(int userId);

    List<Permissions> getPermissionsByRoleId(int roleId);

    List<Permissions> findPermissionsByRoleIdWithoutParentIsNull(int roleId);
    List<Tree> getMenuPermissions();

}
