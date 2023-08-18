package com.wxjw.controller.openapi;

import com.wxjw.common.BaseResponse;
import com.wxjw.common.ResultUtil;
import com.wxjw.dal.dao.ExcelInfoMapper;
import com.wxjw.dal.pojo.ErrorCode;
import com.wxjw.dal.pojo.data.ExportSheet.ExportSheetData;
import com.wxjw.service.ExportSheetService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 导出Excel表格
 *
 * @author 筱锋xiao_lfeng
 */
@RestController
@RequestMapping("/openapi/export")
public class ExportSheetController {

    @Resource
    private ExcelInfoMapper excelInfoMapper;

    @PostMapping("/sheet")
    public ResponseEntity<BaseResponse<Object>> exportSheet(@RequestBody ExportSheetData requestBody) {
        if ("exportfile".equals(requestBody.getAction())) {
            ExportSheetService exportSheetService = new ExportSheetService(excelInfoMapper);
            exportSheetService.exportSheet(requestBody);
            return exportSheetService.getReturnResult();
        } else {
            return ResultUtil.error(ErrorCode.PARAMETER_ERROR);
        }
    }
}
