package com.lulu.controller;

import com.lulu.model.entity.Result;
import com.lulu.model.entity.User;
import com.lulu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Slf4j
@CrossOrigin(origins = "*")
public class RegisterController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result registerUser(@RequestBody User registrationRequest) {
        int code = userService.registerUser(registrationRequest);
        if (code == -1) {
            return Result.error("帳號名稱已被使用");
        }
        if (code == -2) {
            return Result.error("信箱已被使用");
        }
        return Result.success("申請成功");
    }
}
