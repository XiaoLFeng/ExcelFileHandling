package com.wxjw.dal.pojo.data.GetUploadFile;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 *
 * @author 筱锋xiao_lfeng
 */
@Data
@Accessors(chain = true)
public class GetUploadFileData {
    private String action;
    private String userid;
    @Nullable
    private String filelib;
    private Object files;
}
