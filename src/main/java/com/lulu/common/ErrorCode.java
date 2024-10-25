package com.lulu.common;

public enum ErrorCode {
    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "請求參數錯誤"),
    NOT_LOGIN_ERROR(40100, "未登入"),
    NO_AUTH_ERROR(40101, "無權限");
    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
