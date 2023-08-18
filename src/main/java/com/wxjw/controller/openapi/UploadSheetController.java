package com.wxjw.controller.openapi;

import com.wxjw.common.BaseResponse;
import com.wxjw.common.ResultUtil;
import com.wxjw.dal.dao.ExcelInfoMapper;
import com.wxjw.dal.pojo.ErrorCode;
import com.wxjw.dal.pojo.data.UploadSheet.UploadSheetData;
import com.wxjw.service.UploadSheetService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller 更新 Sheet
 *
 * @author 筱锋xiao_lfeng
 */
@RestController
@RequestMapping("/openapi/update")
public class UploadSheetController {
    @Resource
    private ExcelInfoMapper excelInfoMapper;

    @PostMapping("/sheet")
    public ResponseEntity<BaseResponse<Object>> deleteFile(@RequestBody @NotNull UploadSheetData<Object> requestBody) {
        if ("updatefile".equals(requestBody.getAction())) {
            UploadSheetService updateSheetService = new UploadSheetService(excelInfoMapper);
            updateSheetService.updateSheet(requestBody);
            return updateSheetService.getReturnResult();
        } else {
            return ResultUtil.error(ErrorCode.PARAMETER_ERROR);
        }
    }
}
