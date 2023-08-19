package com.wxjw.service;

import com.wxjw.common.BaseResponse;
import com.wxjw.common.ResultUtil;
import com.wxjw.dal.dao.ExcelInfoMapper;
import com.wxjw.dal.pojo.ErrorCode;
import com.wxjw.dal.pojo.data.UploadSheet.UploadSheetData;
import com.wxjw.dal.pojo.entity.ExcelInfoEntity;
import lombok.Getter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Service 更新 Sheet
 *
 * @author 筱锋xiao_lfeng
 */
@Service
public class UploadSheetService {

    private final ExcelInfoMapper excelInfoMapper;
    @Getter
    private ResponseEntity<BaseResponse<Object>> returnResult;

    public UploadSheetService(ExcelInfoMapper excelInfoMapper) {
        this.excelInfoMapper = excelInfoMapper;
    }

    public void updateSheet(@NotNull UploadSheetData<Object> requestBody) {
        // 获取文件
        ExcelInfoEntity excelInfo = excelInfoMapper.getExcelForId(requestBody.getNodeId());
        if (excelInfo != null && excelInfo.getId() != null) {
            // 获取文件
            String fileName = excelInfo.getFileName();
            String sheetName = excelInfo.getSheetName();
            // 综合路径
            String filePathForXlsx = "./src/main/resources/excel/" + fileName + "/" + sheetName + ".xlsx";
            String filePathForXls = "./src/main/resources/excel/" + fileName + "/" + sheetName + ".xls";
            Workbook workbook;
            Path path = Path.of(filePathForXlsx);
            try {
                FileInputStream fileInputStream;
                if (Files.exists(path)) {
                    fileInputStream = new FileInputStream(filePathForXlsx);
                } else {
                    fileInputStream = new FileInputStream(filePathForXls);
                }
                workbook = new XSSFWorkbook(fileInputStream);
                // 关闭流
                fileInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // 获取 sheet
            Sheet sheet = workbook.getSheet(sheetName);
            // 获取总行数
            int rowNum = sheet.getLastRowNum();
            int columnNum = 0;
            for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
                if (columnNum < sheet.getRow(i).getLastCellNum()) {
                    columnNum = sheet.getRow(i).getLastCellNum();
                }
            }
            // 获取更新单元格信息
            for (int i = 0; i < requestBody.getCellInfo().size(); i++) {
                for (int j = 0; j < requestBody.getCellInfo().get(i).size(); j++) {
                    if (sheet.getPhysicalNumberOfRows() > i) {
                        if (sheet.getRow(i).getPhysicalNumberOfCells() > j) {
                            sheet.getRow(i).getCell(j).setCellValue(String.valueOf(requestBody.getCellInfo().get(i).get(j)));
                        } else {
                            sheet.getRow(i).createCell(j).setCellValue(String.valueOf(requestBody.getCellInfo().get(i).get(j)));
                        }
                    } else {
                        sheet.createRow(i).createCell(j).setCellValue(String.valueOf(requestBody.getCellInfo().get(i).get(j)));
                    }
                }
            }
            // 保存数据表
            try {
                FileOutputStream fileOutputStream;
                if (Files.exists(path)) {
                    fileOutputStream = new FileOutputStream(filePathForXlsx);
                } else {
                    fileOutputStream = new FileOutputStream(filePathForXls);
                }
                workbook.write(fileOutputStream);
                // 关闭流
                fileOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // 数据修改
            ExcelInfoEntity excelInfoUpdate = new ExcelInfoEntity();
            excelInfoUpdate
                    .setId(excelInfo.getId())
                    .setFileName(excelInfo.getFileName())
                    .setSheetName(excelInfo.getSheetName())
                    .setTableName(excelInfo.getTableName())
                    .setType(excelInfo.getType())
                    .setCreateBy(excelInfo.getCreateBy())
                    .setParentId(excelInfo.getParentId())
                    .setUpdateBy(excelInfo.getCreateBy())
                    .setCreateTime(excelInfo.getCreateTime())
                    .setCreateBy(excelInfo.getCreateBy())
                    .setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if (excelInfoMapper.updateExcelInfo(excelInfoUpdate)) {
                returnResult = ResultUtil.success();
            } else {
                returnResult = ResultUtil.error(ErrorCode.DATA_UPDATE_FAILURE);
            }
        } else {
            returnResult = ResultUtil.error(ErrorCode.DATA_IS_EMPTY);
        }
    }
}
