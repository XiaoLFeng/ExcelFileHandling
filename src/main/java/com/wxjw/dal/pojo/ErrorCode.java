package com.wxjw.dal.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误码
 *
 * @author 筱锋xiao_lfeng
 */
@AllArgsConstructor
@Getter
public enum ErrorCode {
    DATA_IS_EMPTY("DataIsEmpty", 40010, "数据为空", HttpCode.BAD_REQUEST),
    PARAMETER_ERROR("ParameterError", 40011, "参数错误", HttpCode.BAD_REQUEST),
    DATA_DUPLICATION("DataDuplication", 40012, "数据重复", HttpCode.BAD_REQUEST),
    DATA_WRITE_FAILURE("DataWriteFailure", 40013, "数据写入失败", HttpCode.BAD_REQUEST),
    FILE_TYPE_IS_INCORRECT("FileTypeIncorrect", 40014, "文件类型错误", HttpCode.BAD_REQUEST),
    FILE_CREATION_FAILED("FileCreationFailed", 40015, "文件创建失败", HttpCode.BAD_REQUEST),
    FILE_ALREADY_EXISTS("FileAlreadyExists", 40016, "文件已经存在", HttpCode.BAD_REQUEST);

    private final String output;
    private final int code;
    private final String message;
    private final HttpCode httpCode;
}
