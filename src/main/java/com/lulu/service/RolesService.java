package com.lulu.service;

import com.lulu.model.entity.PageBean;
import com.lulu.model.entity.Roles;

import java.util.List;

public interface RolesService{
    Roles findById(int id);
    PageBean findAll(int page, int pageSize);
    void createRole(Roles role);
    void updateRole(Roles role,Integer id);
    void deleteRole(int id);
    Roles findRoleById(int id);
    void assignPermissionToRole(int roleId, List<Integer> permissionsList);
    void removePermissionFromRole(int roleId);

}
