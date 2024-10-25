package com.lulu.config;

import com.lulu.interceptor.LoginCheckInterceptor;
import com.lulu.interceptor.PermissionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private PermissionInterceptor permissionInterceptor;
    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-ui/**",
                        "/v3/api-docs", "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/error",
                        "/webjars/**");
// TODO

//        registry.addInterceptor(permissionInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/swagger-ui/**",
//                        "/v3/api-docs",
//                        "/swagger-resources/**",
//                        "/swagger-ui.html",
//                        "/error",
//                        "/webjars/**");
//

    }
}

