package com.wxjw.controller.openapi;

import com.wxjw.common.BaseResponse;
import com.wxjw.common.ResultUtil;
import com.wxjw.dal.dao.ExcelInfoMapper;
import com.wxjw.dal.pojo.ErrorCode;
import com.wxjw.dal.pojo.data.GetUploadFile.GetUploadFileData;
import com.wxjw.service.GetUploadFileService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件上传
 *
 * @author 筱锋xiao_lfeng
 */
@RestController
@RequestMapping("/openapi/files")
public class GetUploadFileController {
    @Resource
    private ExcelInfoMapper excelInfoMapper;

    @PostMapping("/upload")
    public ResponseEntity<BaseResponse<Object>> getUploadFile(@RequestBody @NotNull GetUploadFileData getUploadFileData) {
        if ("importfile".equals(getUploadFileData.getAction())) {
            GetUploadFileService getUploadFileService = new GetUploadFileService(excelInfoMapper);
            getUploadFileService.uploadFileService(getUploadFileData);
            return getUploadFileService.getReturnResult();
        } else {
            return ResultUtil.error(ErrorCode.PARAMETER_ERROR);
        }
    }
}
