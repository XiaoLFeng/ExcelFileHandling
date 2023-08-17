package com.wxjw.controller.openapi;

import com.wxjw.common.BaseResponse;
import com.wxjw.common.ResultUtil;
import com.wxjw.dal.dao.ExcelInfoMapper;
import com.wxjw.dal.pojo.ErrorCode;
import com.wxjw.dal.pojo.data.getFileTree.GetFileTreeResultBody;
import com.wxjw.service.GetFileTreeService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller 获取文件树
 *
 * @author 筱锋xiao_lfeng
 */

@RestController
@RequestMapping("/openapi/")
public class GetFileTreeController {
    @Resource
    ExcelInfoMapper excelInfoMapper;

    @PostMapping("/files/tree")
    public ResponseEntity<BaseResponse<Object>> getFileTree(@RequestBody @NotNull GetFileTreeResultBody resultBody) {
        if ("getfiletree".equals(resultBody.getAction())) {
            return new GetFileTreeService().getFileTreeService(excelInfoMapper);
        } else {
            return ResultUtil.error(ErrorCode.PARAMETER_ERROR);
        }
    }
}
