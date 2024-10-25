package com.lulu.controller;

import com.lulu.annotations.RequestPermission;
import com.lulu.aop.ApiPermission;
import com.lulu.model.entity.PageBean;
import com.lulu.model.entity.Result;
import com.lulu.model.entity.Roles;
import com.lulu.service.RolesService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "*")
public class RolesController {

    @Autowired
    private RolesService rolesService;

    @ApiPermission({"權限管理"})  // 這個 API 屬於 權限管理頁面
    @Operation(summary = "使用者的角色", description = "")
    @GetMapping("/{id}")
    public Result getRoleById(@PathVariable int id) {
        Roles role = rolesService.findById(id);
        return Result.success(role);
    }

    @ApiPermission({"權限管理"})  // 這個 API 屬於 權限管理頁面
    @Operation(summary = "角色清單", description = "分頁查詢角色清單")
    @GetMapping
    public Result getAllRoles(@RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer pageSize) {
        PageBean pageBean = rolesService.findAll(page, pageSize);
        return Result.success(pageBean);
    }

    @ApiPermission({"權限管理"})  // 這個 API 屬於 權限管理頁面
    @PostMapping
    @RequestPermission(name="新增角色", parentId="6", tag="權限")
    @Operation(summary = "新增角色", description = "")
    public Result createRole(@RequestBody Roles role) {
        rolesService.createRole(role);
        return Result.success("成功新增角色");
    }

    @ApiPermission({"權限管理"})  // 這個 API 屬於 權限管理頁面
    @PutMapping("/{id}")
    @Operation(summary = "更新角色", description = "")
    public Result updateRole(@RequestBody Roles role,@PathVariable Integer id) {
        rolesService.updateRole(role,id);
        return Result.success("成功更新角色");
    }

    @ApiPermission({"權限管理"})  // 這個 API 屬於 權限管理頁面
    @Operation(summary = "刪除角色", description = "")
    @DeleteMapping("/{id}")
    public Result deleteRole(@PathVariable int id) {
        try{
            rolesService.deleteRole(id);
        }
        catch (RuntimeException message){
            return Result.error("有使用者使用該角色");
        }

        return Result.success("成功刪除角色");
    }
}
