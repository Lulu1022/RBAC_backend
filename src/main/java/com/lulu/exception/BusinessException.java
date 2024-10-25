package com.lulu.exception;

import com.lulu.common.ErrorCode;

public class BusinessException extends RuntimeException  {

    /**
     * 錯誤碼
     */
    private final int code;


    /**
     * 使用指定錯誤碼枚舉初始化異常。
     *
     * @param errorCode 錯誤碼枚舉
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }


}
