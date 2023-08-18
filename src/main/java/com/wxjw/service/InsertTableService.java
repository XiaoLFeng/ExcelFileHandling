package com.wxjw.service;

import com.wxjw.common.BaseResponse;
import com.wxjw.common.ResultUtil;
import com.wxjw.dal.dao.ExcelInfoMapper;
import com.wxjw.dal.pojo.ErrorCode;
import com.wxjw.dal.pojo.data.RetrieveFiles.InsertTableData;
import com.wxjw.dal.pojo.entity.ExcelInfoEntity;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Service 插入数据内容
 *
 * @author 筱锋xiao_lfeng
 */
@Getter
@Service
public class InsertTableService {
    private ResponseEntity<BaseResponse<Object>> operationStatus;
    // 暂未设计
    @Getter
    private String validationErrorMessage = null;

    public void insertLogic(InsertTableData resultBody, ExcelInfoMapper excelInfoMapper) {
        // 搜索数据是否存在
        ArrayList<ExcelInfoEntity> excelInfoList = (ArrayList<ExcelInfoEntity>) excelInfoMapper.getAllExcelFilesNameNoRepetition(resultBody.getFile_name());
        if (!excelInfoList.isEmpty()) {
            // 检查数据是否匹配
            excelInfoList.forEach(excelInfoEntity -> {
                if (Objects.equals(excelInfoEntity.getSheetName(), resultBody.getSheet_name())) {
                    validationErrorMessage = "数据重复";
                    operationStatus = ResultUtil.error(ErrorCode.DATA_DUPLICATION);
                }
            });
            return;
        }
        // 执行插入
        if (validationErrorMessage == null) {
            // 插入数据
            ExcelInfoEntity excelInfoEntity = new ExcelInfoEntity();
            excelInfoEntity
                    .setFileName(resultBody.getFile_name())
                    .setSheetName(resultBody.getSheet_name())
                    .setTableName(resultBody.getTable_name())
                    .setType(resultBody.getType())
                    .setCreateBy(resultBody.getCreate_by())
                    .setUpdateBy(resultBody.getUpdate_by())
                    .setCreateTime(resultBody.getCreate_time())
                    .setUpdateTime(resultBody.getUpdate_time());
            if (excelInfoMapper.insertExcelInfo(excelInfoEntity)) {
                operationStatus = ResultUtil.success();
            } else {
                operationStatus = ResultUtil.error(ErrorCode.DATA_WRITE_FAILURE);
            }
        } else {
            operationStatus = ResultUtil.error(ErrorCode.DATA_DUPLICATION);
        }
    }
}
