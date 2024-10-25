package com.lulu.controller;

import com.lulu.aop.ApiPermission;
import com.lulu.model.entity.Permissions;
import com.lulu.model.entity.Result;
import com.lulu.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/permissions")
@CrossOrigin(origins = "*")
public class PermissionController {
    @Autowired
    private PermissionService permissionsService;
    @ApiPermission({"權限管理"})  // 這個 API 屬於 權限管理頁面
    @Operation(summary = "權限清單", description = "")
    @GetMapping
    public Result getAllPermissions() {
        List<Permissions> permissions = permissionsService.findAll();
        return Result.success(permissions);
    }

    @Operation(summary = "新增權限(暫無使用)", description = "")
    @PostMapping
    public Result createPermission(@RequestBody Permissions permission) {
        permissionsService.createPermission(permission);
        return Result.success("成功新增權限");
    }

    @Operation(summary = "更新權限(暫無使用)", description = "")
    @PutMapping
    public Result updatePermission(@RequestBody Permissions permission) {
        permissionsService.updatePermission(permission);
        return Result.success("成功更新權限");
    }

    @Operation(summary = "刪除權限(暫無使用)", description = "")
    @DeleteMapping("/{id}")
    public Result deletePermission(@PathVariable int id) {
        permissionsService.deletePermission(id);
        return Result.success("成功刪除權限");
    }


}
