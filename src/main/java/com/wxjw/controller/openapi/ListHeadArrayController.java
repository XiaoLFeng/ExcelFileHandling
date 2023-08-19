package com.wxjw.controller.openapi;

import com.wxjw.common.BaseResponse;
import com.wxjw.common.ResultUtil;
import com.wxjw.dal.dao.ExcelInfoMapper;
import com.wxjw.dal.pojo.ErrorCode;
import com.wxjw.dal.pojo.data.ListHeadArray.ListHeadArrayData;
import com.wxjw.dal.pojo.entity.ExcelInfoEntity;
import com.wxjw.service.ListHeadArrayService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller 表头数组控制
 *
 * @author 筱锋xiao_lfeng
 */
@RestController
@RequestMapping("/openapi/header")
public class ListHeadArrayController {
    @Resource
    private ExcelInfoMapper excelInfoMapper;

    @PostMapping("/get")
    public ResponseEntity<BaseResponse<Object>> getListHeadArray(@RequestBody @NotNull ListHeadArrayData requestBody) {
        if ("getfileIds".equals(requestBody.getAction())) {
            ListHeadArrayService service = new ListHeadArrayService(excelInfoMapper);
            service.getListHeadArray(requestBody);
            return service.getReturnResult();
        } else {
            return ResultUtil.error(ErrorCode.PARAMETER_ERROR);
        }
    }
}
