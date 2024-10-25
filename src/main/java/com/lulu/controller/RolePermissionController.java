package com.lulu.controller;

import com.lulu.aop.ApiPermission;
import com.lulu.model.entity.Permissions;
import com.lulu.model.entity.Result;
import com.lulu.model.entity.Roles;
import com.lulu.model.entity.Tree;
import com.lulu.service.PermissionService;
import com.lulu.service.RolesService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/roles")
@CrossOrigin(origins = "*")
public class RolePermissionController {

    @Autowired
    private RolesService roleService;
    @Autowired
    private PermissionService permissionService;


    //@ApiPermission({"權限管理"})  // 這個 API 屬於 權限管理頁面
    @Operation(summary = "設定角色權限", description = "")
    @PostMapping("/{roleId}/permissions")
    public Result assignPermissionToRole(@PathVariable int roleId, @RequestBody List<Integer> permissionsList) {
        // 檢查角色是否存在
        Roles role = roleService.findRoleById(roleId);
        log.info(String.valueOf(role));
        if (role == null) {
            return Result.error("角色不存在");
        }

        // 將權限指派給角色
        roleService.assignPermissionToRole(roleId, permissionsList);

        return Result.success("成功賦予角色權限");
    }


    @ApiPermission({"權限管理"})  // 這個 API 屬於 權限管理頁面
    @Operation(summary = "移除角色權限", description = "")
    @DeleteMapping("/{roleId}/permissions")
    public Result removePermissionFromRole(@PathVariable Integer roleId) {
        // 檢查角色是否存在
        Roles role = roleService.findRoleById(roleId);

        if (role == null) {
            return Result.error("角色不存在");
        }

        // 將權限從角色中移除
        roleService.removePermissionFromRole(roleId);

        return Result.success("成功將權限從角色移除");
    }

    @Operation(summary = "獲取角色擁有的權限", description = "")
    @GetMapping("/{roleId}/permissions")
    public Result getPermissionsByRoleId(@PathVariable int roleId) {
        List<Permissions> userPermissionList = permissionService.getPermissionsByRoleId(roleId);
        return Result.success(userPermissionList);
    }

    @Operation(summary = "獲取角色擁有的權限", description = "Tree 預設勾選的權限項目，只有 left nodes")
    @GetMapping("/{roleId}/treepermissions")
    public Result findPermissionsByRoleIdWithoutParentIsNull(@PathVariable int roleId) {
        List<Permissions> userPermissionList = permissionService.findPermissionsByRoleIdWithoutParentIsNull(roleId);
        return Result.success(userPermissionList);
    }

    @ApiPermission({"權限管理"})  // 這個 API 屬於 權限管理頁面
    @Operation(summary = "權限(Tree)清單", description = "")
    @GetMapping("/menupermission")
    public Result getMenuPermissions() {
        List<Tree> userPermissionList = permissionService.getMenuPermissions();
        return Result.success(userPermissionList);
    }


}
