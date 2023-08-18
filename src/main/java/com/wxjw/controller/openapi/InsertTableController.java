package com.wxjw.controller.openapi;


import com.wxjw.common.BaseResponse;
import com.wxjw.common.ResultUtil;
import com.wxjw.dal.dao.ExcelInfoMapper;
import com.wxjw.dal.pojo.ErrorCode;
import com.wxjw.dal.pojo.data.RetrieveFiles.InsertTableData;
import com.wxjw.service.InsertTableService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller 插入数据内容
 *
 * @author 筱锋xiao_lfeng
 */
@RestController
@RequestMapping("/openapi/files")
public class InsertTableController {
    @Resource
    private ExcelInfoMapper excelInfoMapper;

    @PostMapping("/insert")
    public ResponseEntity<BaseResponse<Object>> insertFunction(@RequestBody @NotNull InsertTableData resultBody) {
        if ("insert".equals(resultBody.getAction())) {
            InsertTableService insertTableService = new InsertTableService();
            insertTableService.insertLogic(resultBody, excelInfoMapper);
            return insertTableService.getOperationStatus();
        } else {
            return ResultUtil.error(ErrorCode.PARAMETER_ERROR);
        }
    }
}
