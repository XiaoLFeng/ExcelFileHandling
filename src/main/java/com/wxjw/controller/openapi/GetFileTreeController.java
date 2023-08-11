package com.wxjw.controller.openapi;

import com.wxjw.common.Result;
import com.wxjw.dal.dao.ExcelInfoMapper;
import com.wxjw.service.GetFileTreeService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

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
    public ResponseEntity<Result<Object>> getFileTree(@RequestBody @NotNull HashMap<String, String> resultBody) {
        String getResult = resultBody.get("action");
        if ("getfiletree".equals(getResult)) {
            return ResponseEntity.ok()
                    .body(new GetFileTreeService().getFileTreeService(excelInfoMapper));
        } else {
            return ResponseEntity.badRequest()
                    .body(new Result<>(403, null, "参数错误"));
        }
    }
}
