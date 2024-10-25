package com.lulu.service.impl;

import com.lulu.exception.RoleDeletionException;
import com.lulu.mapper.PermissionsMapper;
import com.lulu.mapper.RolesMapper;
import com.lulu.model.entity.PageBean;
import com.lulu.model.entity.Roles;
import com.lulu.service.RolesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Slf4j
public class RoleServiceImpl implements RolesService {
    @Autowired
    private  RolesMapper rolesMapper;
    @Autowired
    private PermissionsMapper permissionsMapper;

    @Override
    public Roles findById(int id) {
        return rolesMapper.findById(id);
    }

    @Override
    public PageBean findAll(int page, int pageSize) {
        //1.獲得紀錄總數
        Long count = rolesMapper.count();
        //2.獲得分頁查詢結果
        Integer start = (page - 1) * pageSize;
        List<Roles> userList = rolesMapper.findAll(start, pageSize);
        //3.封裝成 PageBean
        PageBean pageBean = new PageBean(count,userList);
        return pageBean;
    }

    @Override
    public void createRole(Roles role) {
        rolesMapper.createRole(role);
    }

    @Override
    public void updateRole(Roles role,Integer id) {
        rolesMapper.updateRole(role,id);
    }

    @Override
    @Transactional
    // TODO 需要返回 "有使用者使用該角色"
    public void deleteRole(int id) {
        try {
            rolesMapper.deleteRole(id);
            permissionsMapper.deletePermissionByRoleId(id);
        } catch (Exception e) {
            throw new RoleDeletionException("有使用者使用該角色");
        }

    }

    @Override
    public Roles findRoleById(int id) {
        return rolesMapper.findRoleById(id);
    }


    @Override
    public void assignPermissionToRole(int roleId, List<Integer> permissionsList) {
        rolesMapper.assignPermissionToRole(roleId, permissionsList);
    }

    @Override
    public void removePermissionFromRole(int roleId) {
        rolesMapper.removePermissionFromRole(roleId);
    }
}
