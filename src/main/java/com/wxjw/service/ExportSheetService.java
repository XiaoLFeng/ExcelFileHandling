package com.wxjw.service;

import com.wxjw.common.BaseResponse;
import com.wxjw.common.ResultUtil;
import com.wxjw.dal.dao.ExcelInfoMapper;
import com.wxjw.dal.pojo.ErrorCode;
import com.wxjw.dal.pojo.data.ExportSheet.ExportSheetData;
import com.wxjw.dal.pojo.entity.ExcelInfoEntity;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * Service 操作导出 Sheet
 *
 * @author 筱锋xiao_lfeng
 */
@Service
public class ExportSheetService {

    private final ExcelInfoMapper excelInfoMapper;
    @Getter
    private ResponseEntity<BaseResponse<Object>> returnResult;

    public ExportSheetService(ExcelInfoMapper excelInfoMapper) {
        this.excelInfoMapper = excelInfoMapper;
    }

    public void exportSheet(@NotNull ExportSheetData requestBody) {
        // 检查此 id 是否在数据库中存在
        ExcelInfoEntity getExcel = excelInfoMapper.getExcelForId(requestBody.getNodeId());
        if (getExcel != null && getExcel.getId() != null) {
            // 获取数据
            String getFileName = getExcel.getFileName();
            String getSheetName = getExcel.getSheetName();
            // 构建文件夹位置
            String filePathForXlsx = "./src/main/resources/excel/" + getFileName + "/" + getSheetName + ".xlsx";
            String filePathForXls = "./src/main/resources/excel/" + getFileName + "/" + getSheetName + ".xls";
            String getBase64;
            try {
                InputStream inputStream;
                if (Files.exists(Paths.get(filePathForXlsx))) {
                    inputStream = new FileInputStream(filePathForXlsx);
                } else {
                    inputStream = new FileInputStream(filePathForXls);
                }
                getBase64 = Base64.getEncoder().encodeToString(inputStream.readAllBytes());
                // 关闭输入流
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // 输出结果
            returnResult = ResultUtil.success(getBase64);
        } else {
            // 为空操作
            returnResult = ResultUtil.error(ErrorCode.THERE_IS_NO_SUCH_RECORD);
        }
    }
}
