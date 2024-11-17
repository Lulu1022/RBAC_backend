package com.lulu.interceptor;

import com.lulu.model.entity.Result;
import com.lulu.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String url = request.getRequestURL().toString();

        //1.獲取請求的 url
        log.info("請求的 url:{}", url);
        //2.判斷如果 url
        if (url.contains("login") || url.contains("captcha") || url.contains("register")) {
            log.info("包含 login 或 captcha 或 register 放行");
            return true;
        }

        //3.獲取請求頭的令牌(token)Authorization
        String jwt = request.getHeader("Authorization");
        log.info(jwt);
        if(jwt == null){
            log.info("jwt 不存在");
        }
        if (jwt.startsWith("Bearer")){
            jwt = jwt.substring(7);
        }

        //4.判斷令牌是否存在
        if (!StringUtils.hasLength(jwt)) {
            log.info("請求 token 為空");
            Result error = Result.error("請求 token 為空");

            return false;
        }
        //5.判斷是否過期
        if (jwt == null || JwtUtils.isTokenExpired(jwt)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return false;
        }
        //6.解析令牌
        try {
            JwtUtils.parseJWT(jwt);
            log.info("5.解析令牌");
        } catch (Exception e) {
            e.getMessage();

            return false;
        }
        //7.合法放行
        log.info("合法放行");
        return true;
    }

}
