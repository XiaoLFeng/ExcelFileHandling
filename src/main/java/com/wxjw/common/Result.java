package com.wxjw.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 筱锋xiao_lfeng
 */
@Data
@AllArgsConstructor
public class Result<T> {
    private int code;
    private T data;
    private String msg;

    public Result() {
    }
}
