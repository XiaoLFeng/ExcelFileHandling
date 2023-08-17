package com.wxjw.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wxjw.dal.pojo.ErrorCode;
import com.wxjw.dal.pojo.HttpCode;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * 响应体定义
 *
 * @author 筱锋xiao_lfeng
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    private int code;
    private T data;
    private String msg;

    public BaseResponse(@NotNull HttpCode httpCode) {
        this(httpCode.getCode(), httpCode.getDescription(), null);
    }

    public BaseResponse(@NotNull HttpCode httpCode, String message) {
        this(httpCode.getCode(), message, null);
    }

    public BaseResponse(@NotNull ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public BaseResponse(@NotNull ErrorCode errorCode, T data) {
        this(errorCode.getCode(), errorCode.getMessage(), data);
    }

    public BaseResponse(@NotNull ErrorCode errorCode, String message) {
        this(errorCode.getCode(), message, null);
    }

    public BaseResponse(@NotNull HttpCode httpCode, String message, T data) {
        this(httpCode.getCode(), message, data);
    }

    public BaseResponse(int code, String message) {
        this(code, message, null);
    }

    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }
}
