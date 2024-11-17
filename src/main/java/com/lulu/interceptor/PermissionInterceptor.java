package com.lulu.interceptor;

import com.lulu.annotations.RequestPermission;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Slf4j
@Component

public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        List<String> sessionPermission =(List<String>)request.getSession().getAttribute("permissionList");
        if (sessionPermission == null) {
            log.warn("PermissionInterceptor: 未找到 session 中的 permissionList");
            return false;
        }
        log.info("在 PermissionInterceptor 取得後端 session 中存的 permission :{}", sessionPermission);

        if(handler instanceof HandlerMethod) {

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (!handlerMethod.hasMethodAnnotation(RequestPermission.class)) {
                return true;
            }

            //所有被標註 annotation 的 API
            RequestPermission permission = handlerMethod.getMethodAnnotation(RequestPermission.class);
            String name = permission.name();
            log.info("所有被標註 annotation 的 API:{}",name);
            if (!sessionPermission.contains(name)) {
                //response.sendRedirect("/permission/nopermission");
                log.info("您沒有權限呼叫該 API");
                return false;
            }

        }
        log.info("呼叫 API 成功");
        return true;
    }
}


