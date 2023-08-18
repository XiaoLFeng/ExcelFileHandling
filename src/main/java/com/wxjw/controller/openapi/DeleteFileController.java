package com.wxjw.controller.openapi;

import com.wxjw.common.BaseResponse;
import com.wxjw.common.ResultUtil;
import com.wxjw.dal.dao.ExcelInfoMapper;
import com.wxjw.dal.pojo.ErrorCode;
import com.wxjw.dal.pojo.data.DeleteFile.DeleteFileData;
import com.wxjw.service.DeleteFileService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller 删除文件（仅需删除数据库）
 *
 * @author 筱锋xiao_lfeng
 */
@RestController
@RequestMapping("/openapi/delete")
public class DeleteFileController {

    @Resource
    private ExcelInfoMapper excelInfoMapper;

    @PostMapping("/file")
    public ResponseEntity<BaseResponse<Object>> deleteFile(@RequestBody @NotNull DeleteFileData requestBody) {
        if ("deletefile".equals(requestBody.getAction())) {
            DeleteFileService deleteFileService = new DeleteFileService(excelInfoMapper);
            deleteFileService.deleteFile(requestBody);
            return deleteFileService.getReturnResult();
        } else {
            return ResultUtil.error(ErrorCode.PARAMETER_ERROR);
        }
    }
}
