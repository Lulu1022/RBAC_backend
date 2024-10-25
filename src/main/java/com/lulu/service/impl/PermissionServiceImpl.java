package com.lulu.service.impl;

import com.lulu.annotations.RequestPermission;
import com.lulu.mapper.PermissionsMapper;
import com.lulu.model.entity.Permissions;
import com.lulu.model.entity.RolePermission;
import com.lulu.model.entity.Tree;
import com.lulu.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionsMapper permissionsMapper;

    @Autowired
    private ApplicationContext ac;

    Permissions permission = new Permissions();

    Set<String> set = new HashSet<>();


    @Override
    public void savePermission() {
        // 先查询数据库 找到我们所有的权限放到Set中
        List<Permissions> permissions = permissionsMapper.findAll();
        if (permissions != null) {
            permissions.forEach((permission) -> {
                set.add(permission.getPermissionName());
            });
        }

        log.info("permissions:{}", permissions);

        RequestMappingHandlerMapping handlerMapping = ac.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = handlerMapping.getHandlerMethods();

        Collection<HandlerMethod> methods = handlerMethodMap.values();
        //得到方法上的注解  标记我们想要的注解
        for (HandlerMethod method : methods) {

            //判断每一个方法上有没有这个注解，如果没有就跳过
            RequestPermission requestPermission = method.getMethodAnnotation(RequestPermission.class);

            if (requestPermission == null)
                continue;


            String name = requestPermission.name();
            Integer parentId= Integer.valueOf(requestPermission.parentId());
            String tag = requestPermission.tag();

            //为了防止 重复存入，我们使用Set集合 的无序不可重复 特性存储
            //能到这里说明是有值的，那我就拿出来，存到数据库中
            if (set.contains(name)) continue;
            // 如果我们存过了 那就直接跳过
            //把这个 表达式 存到 set里面
            set.add(name);
            permission.setPermissionName(name);
            permission.setParentId(parentId);
            permission.setTag(tag);
            permissionsMapper.createPermission(permission);
        }

    }

    @Override
    public Permissions findPermissionById(int id) {
        return permissionsMapper.findPermissionById(id);
    }


    @Override
    public List<Permissions> findAll() {
        return permissionsMapper.findAll();
    }

    @Override
    public void createPermission(Permissions permission) {
        permissionsMapper.createPermission(permission);
    }

    @Override
    public void updatePermission(Permissions permission) {
        permissionsMapper.updatePermission(permission);
    }

    @Override
    public void deletePermission(int id) {
        permissionsMapper.deletePermission(id);
    }

    @Override
    public List<Permissions> findPermissionsByUserId(int userId) {
        return permissionsMapper.findPermissionsByUserId(userId);
    }

    @Override
    public List<String> findPermissionsPathByUserId(int userId) {
        return permissionsMapper.findPermissionsPathByUserId(userId);
    }

    @Override
    public List<Permissions> getPermissionsByRoleId(int roleId) {
        return permissionsMapper.findPermissionsByRoleId(roleId);
    }

    @Override
    public List<Permissions> findPermissionsByRoleIdWithoutParentIsNull(int roleId) {
        return permissionsMapper.findPermissionsByRoleIdWithoutParentIsNull(roleId);
    }

    @Override
    public List<Tree> getMenuPermissions() {
        List<RolePermission> rawData = permissionsMapper.findMenuPermissions();
        Map<Integer, Tree> treeMap = new HashMap<>();

        // 第一次遍歷建立所有的節點，並將其放入map中
        for (RolePermission permission : rawData) {
            Tree node = new Tree(permission.getPermissionId(), permission.getParentId(), permission.getPermissionName(), new ArrayList<>(), permission.getTag());
            treeMap.put(permission.getPermissionId(), node);
        }

        // 第二次遍歷建立樹狀結構
        List<Tree> treeList = new ArrayList<>();
        for (Tree node : treeMap.values()) {
            if (node.getParentId() == 0) {
                // 根節點
                treeList.add(node);
            } else {
                // 子節點
                Tree parent = treeMap.get(node.getParentId());
                if (parent != null) {
                    parent.getChildren().add(node);
                }
            }
        }

        return treeList;

    }


}

