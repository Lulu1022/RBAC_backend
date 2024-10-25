package com.lulu.controller;

import com.lulu.model.entity.Permissions;
import com.lulu.model.entity.Result;
import com.lulu.model.entity.User;
import com.lulu.model.vo.UserLoginVo;
import com.lulu.service.PermissionService;
import com.lulu.service.UserService;
import com.lulu.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping
@Slf4j
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {


        // 返回使用者資料，不包含密碼
        UserLoginVo userLoginVo = userService.login(user);

        // 使用者未被啟用
        if(userLoginVo != null && userLoginVo.getStatus() == 0){
            return Result.error("使用者未啟用");
        }


        // 登入成功，生成 JWT，下發 JWT
        if(userLoginVo != null){
            Map<String, Object> claims = new HashMap<>();

            // 根據使用者 id, username 生成 jwt
            claims.put("id", userLoginVo.getUserId());
            claims.put("username", userLoginVo.getUsername());
            String jwt = JwtUtils.generateJwt(claims);

            // 儲存到 Session 中 (JWT, 使用者資訊)
            HttpSession session = request.getSession(true); // 如果 session 不存在則創建
            session.setAttribute("token", jwt);
            session.setAttribute("user", userLoginVo);
            log.info("session ID: " + session.getId());
            session.setAttribute("user", userLoginVo);
            log.info("已保存用戶到 session 中: " + userLoginVo);
            log.info("Session ID in LoginController: " + session.getId());

            // 返回包含 JWT 和 userid 的成功響應
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("token", jwt);
            responseData.put("user", userLoginVo);

            // 返回使用者權限
            List<Permissions> permissionList = permissionService.findPermissionsByUserId(userLoginVo.getUserId());
            ArrayList<String> permissionNameList = new ArrayList<>();
            ArrayList<Integer> permissionIdList = new ArrayList<>();
            for(Permissions permission : permissionList){
                permissionNameList.add(permission.getPermissionName());
                permissionIdList.add(permission.getPermissionId());
            }
            responseData.put("userPermissionId", permissionIdList);
            responseData.put("userPermissionName",permissionNameList);
            return Result.success(responseData);
        }


        return  Result.error("登入失敗");

    }
}
