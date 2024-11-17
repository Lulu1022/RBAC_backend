package com.lulu.aop;

import com.lulu.model.entity.Permissions;
import com.lulu.model.vo.UserLoginVo;
import com.lulu.service.PermissionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class ApiPermissionAspect {
    @Autowired
    PermissionService permissionService;
    @Autowired
    private HttpServletRequest request;

    @Before("@annotation(apiPermission)")
    public void checkApiPermission(JoinPoint joinPoint, ApiPermission apiPermission) {
        log.info("觸發 AOP");
        // 獲取當前使用者的頁面權限
        List<String> currentUserPermission = getCurrentUserPermission();
        log.info("獲取當前使用者的頁面權限: {}, ApiPermission: {}", currentUserPermission, apiPermission);

        // 檢查 API 標註的頁面權限是否包含當前使用者的權限
        /**
         * 1. apiPermission.value()：這是從 @ApiPermission 註解中取得的權限列表，這個列表裡面包含哪些頁面可以使用這個 API
         * ，例如 {"A", "B"} 代表這個 API 可以被屬於 A 和 B 頁面的使用者訪問。
         *
         * 2. currentUserPermission：這是當前使用者擁有的頁面權限，
         * 比如使用者有權限訪問 A 頁面，currentUserPermission 的值是 "A"
         *
         * 3. Arrays.stream(apiPermission.value())：這個方法會將 apiPermission.value()
         * （即 API 可以被哪些頁面訪問的權限）轉換成一個 Stream，Stream 可以看作是對這些權限的一個集合進行遍歷操作
         *
         * 4. permission.equals(currentUserPermission)：這裡的 permission 是遍歷 apiPermission.value() 中的每一個權限（例如 A 或 B）。currentUserPermission 是當前使用者的權限
         * ，所以這行代碼的意思是：檢查 apiPermission.value() 中的任意一個權限是否和 currentUserPermission 相同。
         */;
        boolean hasPermission = Arrays.stream(apiPermission.value())
                .anyMatch(currentUserPermission::contains);


        if (!hasPermission) {
            throw new SecurityException("無權限執行此 API");
        }

    }

    private List<String> getCurrentUserPermission() {

        // 從 session 中取得 userLoginVo 物件
        HttpSession session = request.getSession();
        log.info("Session ID in ApiPermissionAspect: " + session.getId());
        UserLoginVo userLoginVo = (UserLoginVo) session.getAttribute("user");
        if (userLoginVo == null) {
            throw new SecurityException("使用者尚未登入");
        }

        // userLoginVo 有 userId，可以用來查詢該使用者的權限
        int userId = userLoginVo.getUserId();
        List<Permissions> permissionsByUserId = permissionService.findPermissionsByUserId(userId);

        return permissionService.findPermissionsByUserId(userId)
                .stream()
                .map(Permissions::getPermissionName)
                .collect(Collectors.toList());
    }
}
