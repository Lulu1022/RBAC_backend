package com.lulu.controller;

import com.lulu.annotations.RequestPermission;
import com.lulu.common.ErrorCode;
import com.lulu.exception.BusinessException;
import com.lulu.model.dto.user.UserCreateRequest;
import com.lulu.model.entity.PageBean;
import com.lulu.model.entity.Permissions;
import com.lulu.model.entity.Result;
import com.lulu.model.entity.User;
import com.lulu.model.dto.user.UserUpdateRequest;
import com.lulu.service.PermissionService;
import com.lulu.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@Slf4j
@CrossOrigin(origins = "*")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @RequestPermission(name="新增使用者", parentId="5", tag="權限")
    @Operation(summary = "新增使用者", description = "")
    @PostMapping("/users")
    public Result addUser(@RequestBody UserCreateRequest userCreateRequest) {
        if(userCreateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        userService.createUser(userCreateRequest);
        return Result.success("新增使用者成功");
    }

    @RequestPermission(name="刪除使用者" , parentId="5", tag="權限")
    @Operation(summary = "刪除使用者", description = "")
    @DeleteMapping("/users")
    public Result deleteUser(@RequestParam("userId") Integer id) {
        userService.deleteUser(id);
        return Result.success("刪除使用者成功");
    }

    @RequestPermission(name="編輯使用者", parentId="5", tag="權限")
    @Operation(summary = "編輯使用者", description = "")
    @PutMapping("/users")
    public Result updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest.getUserId() == null || userUpdateRequest.getRole() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        userService.updateUser(userUpdateRequest);
        return Result.success("更新成功");
    }

    @Operation(summary = "更新使用者狀態",  description = "")
    @PutMapping("/users/{id}/status")
    public Result updateUserStatus(@PathVariable int id, @RequestParam int status) {

        if(id < 0 || status < 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        userService.updateUserStatus(id, status);
        return Result.success("更新狀態成功");
    }
    @Operation(summary = "查詢使用者", description = "")
    @GetMapping("/users/{id}")
    public Result updateUser(@PathVariable("id") Integer id) {
        User user = userService.getById(id);
        return Result.success(user);
    }


    @RequestPermission(name="使用者列表", parentId="5", tag="權限")
    @Operation(summary = "查詢使用者(分頁)", description = "分頁查詢使用者清單")
    @GetMapping("/users")
    public Result getUsers(@RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分頁查詢參數 {},{}", page, pageSize);
        PageBean pageBean = userService.getUsers(page, pageSize);
        return Result.success(pageBean);
    }

    @Operation(summary = "使用者擁有的權限", description = "")
    @GetMapping("/users/{userId}/permissions")
    public Result getUserPermissions(@PathVariable int userId) {
        List<Permissions> permissionList = permissionService.findPermissionsByUserId(userId);
        return Result.success(permissionList);
    }

    @Operation(summary = "使用者擁有的權限路徑", description = "")
    @GetMapping("/users/{userId}/permissions/path")
    @ResponseBody
    public Result getUserPermissionsPath(@PathVariable int userId) {
        List<String> permissionList = permissionService.findPermissionsPathByUserId(userId);
        return Result.success(permissionList);
    }

}
