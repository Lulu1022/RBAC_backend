package com.lulu.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiPermission {
    String[] value();  // 定義 API 所屬的頁面權限，例如 "A 頁面", "B 頁面"
}
