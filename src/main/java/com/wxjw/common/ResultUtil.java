package com.wxjw.common;

import com.wxjw.dal.pojo.ErrorCode;
import com.wxjw.dal.pojo.HttpCode;
import org.springframework.http.ResponseEntity;

/**
 * Utils 响应体
 *
 * @author 筱锋xiao_lfeng
 */
public class ResultUtil {
    public static ResponseEntity<BaseResponse<Object>> success() {
        return ResponseEntity
                .status(HttpCode.OK.getCode())
                .body(new BaseResponse<>(HttpCode.OK , "Success", "成功"));
    }

    public static ResponseEntity<BaseResponse<Object>> success(String message) {
        return ResponseEntity
                .status(HttpCode.OK.getCode())
                .body(new BaseResponse<>(HttpCode.OK, "Success", message));
    }

    public static ResponseEntity<BaseResponse<Object>> success(Object data) {
        return ResponseEntity
                .status(HttpCode.OK.getCode())
                .body(new BaseResponse<>(HttpCode.OK, "Success", data));
    }

    public static ResponseEntity<BaseResponse<Object>> success(Object data, String message) {
        return ResponseEntity
                .status(HttpCode.OK.getCode())
                .body(new BaseResponse<>(HttpCode.OK, message, data));
    }

    public static ResponseEntity<BaseResponse<Object>> error(HttpCode httpCode, String output, String message) {
        return ResponseEntity
                .status(httpCode.getCode())
                .body(new BaseResponse<>(httpCode, output, message));
    }

    public static ResponseEntity<BaseResponse<Object>> error(HttpCode httpCode, String output, String message, Object data) {
        return ResponseEntity
                .status(httpCode.getCode())
                .body(new BaseResponse<>(httpCode, message, data));
    }

    public static ResponseEntity<BaseResponse<Object>> error(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpCode().getCode())
                .body(new BaseResponse<>(errorCode));
    }

    public static ResponseEntity<BaseResponse<Object>> error(ErrorCode errorCode, String message) {
        return ResponseEntity
                .status(errorCode.getHttpCode().getCode())
                .body(new BaseResponse<>(errorCode, message));
    }

    public static ResponseEntity<BaseResponse<Object>> error(ErrorCode errorCode, Object data) {
        return ResponseEntity
                .status(errorCode.getHttpCode().getCode())
                .body(new BaseResponse<>(errorCode, data));
    }
}
