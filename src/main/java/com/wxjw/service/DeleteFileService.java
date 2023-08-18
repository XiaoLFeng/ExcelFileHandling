package com.wxjw.service;

import com.wxjw.common.BaseResponse;
import com.wxjw.common.ResultUtil;
import com.wxjw.dal.dao.ExcelInfoMapper;
import com.wxjw.dal.pojo.ErrorCode;
import com.wxjw.dal.pojo.data.DeleteFile.DeleteFileData;
import com.wxjw.dal.pojo.entity.ExcelInfoEntity;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service 删除文件控制器（不删除文件，删除数据库记录）
 *
 * @author 筱锋xiao_lfeng
 */
@Service
public class DeleteFileService {
    private final ExcelInfoMapper excelInfoMapper;
    @Getter
    private ResponseEntity<BaseResponse<Object>> returnResult;

    public DeleteFileService(ExcelInfoMapper excelInfoMapper) {
        this.excelInfoMapper = excelInfoMapper;
    }

    public void deleteFile(@NotNull DeleteFileData requestBody) {
        // 检查数据是否存在
        ExcelInfoEntity excelInfo = excelInfoMapper.getExcelForId(requestBody.getNodeId());
        if (excelInfo != null && excelInfo.getId() != null) {
            // 查找到数据执行删除
            if (excelInfoMapper.deleteExcelForId(excelInfo.getId())) {
                returnResult = ResultUtil.success("删除成功");
            } else {
                returnResult = ResultUtil.error(ErrorCode.DATA_DELETE_FAILURE);
            }
        } else {
            returnResult = ResultUtil.error(ErrorCode.DATA_IS_EMPTY);
        }
    }
}
