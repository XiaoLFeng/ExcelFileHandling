package com.wxjw.controller.openapi;

import com.wxjw.common.BaseResponse;
import com.wxjw.common.ResultUtil;
import com.wxjw.dal.dao.ExcelInfoMapper;
import com.wxjw.dal.pojo.ErrorCode;
import com.wxjw.dal.pojo.data.RetrieveFilesData;
import com.wxjw.service.RetrieveFilesService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller 文件检索
 *
 * @author 筱锋xiao_lfeng
 */
@RestController
@RequestMapping("/openapi/retrieve")
public class RetrieveFilesController {

    @Resource
    private ExcelInfoMapper excelInfoMapper;

    @PostMapping("/files")
    public ResponseEntity<BaseResponse<Object>> retrieveFiles(@RequestBody RetrieveFilesData requestBody) {
        if ("search".equals(requestBody.getAction())) {
            RetrieveFilesService retrieveFilesService = new RetrieveFilesService(excelInfoMapper);
            retrieveFilesService.getSearchFile(requestBody);
            return retrieveFilesService.getReturnResult();
        } else {
            return ResultUtil.error(ErrorCode.PARAMETER_ERROR);
        }
    }
}
