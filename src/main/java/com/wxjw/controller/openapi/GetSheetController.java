package com.wxjw.controller.openapi;

import com.wxjw.common.BaseResponse;
import com.wxjw.common.ResultUtil;
import com.wxjw.dal.dao.ExcelInfoMapper;
import com.wxjw.dal.pojo.ErrorCode;
import com.wxjw.dal.pojo.data.GetSheet.GetSheetResultBody;
import com.wxjw.service.GetSheetService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller 获取 sheet
 *
 * @author 筱锋xiao_lfeng
 */
@RestController
@RequestMapping("/openapi/get")
public class GetSheetController {

    @Resource
    private ExcelInfoMapper excelInfoMapper;

    @PostMapping("/sheet")
    public ResponseEntity<BaseResponse<Object>> getSheet(@RequestBody @NotNull GetSheetResultBody resultBody) {
        if ("openflie".equals(resultBody.getAction())) {
            GetSheetService getSheetService = new GetSheetService(excelInfoMapper);
            getSheetService.getSheet(resultBody);
            return getSheetService.getReturnResult();
        } else {
            return ResultUtil.error(ErrorCode.PARAMETER_ERROR);
        }
    }
}
