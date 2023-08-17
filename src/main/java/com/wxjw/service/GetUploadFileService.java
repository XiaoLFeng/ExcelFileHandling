package com.wxjw.service;

import com.wxjw.common.BaseResponse;
import com.wxjw.common.ResultUtil;
import com.wxjw.dal.dao.ExcelInfoMapper;
import com.wxjw.dal.pojo.ErrorCode;
import com.wxjw.dal.pojo.data.GetUploadFile.GetUploadFileData;
import com.wxjw.dal.pojo.entity.ExcelInfoEntity;
import lombok.Getter;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

/**
 * @author 筱锋xiao_lfeng
 */
@Service
public class GetUploadFileService {

    private final ExcelInfoMapper excelInfoMapper;
    @Getter
    private ResponseEntity<BaseResponse<Object>> returnResult;
    @Getter
    private boolean checkType;

    public GetUploadFileService(ExcelInfoMapper excelInfoMapper) {
        this.excelInfoMapper = excelInfoMapper;
    }

    private static boolean isSheetEmpty(@NotNull Sheet sheet) {
        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    if (cell != null && cell.getCellType() != CellType.BLANK) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static void copySheet(@NotNull Sheet sourceSheet, Sheet targetSheet) {
        for (int i = sourceSheet.getFirstRowNum(); i <= sourceSheet.getLastRowNum(); i++) {
            Row sourceRow = sourceSheet.getRow(i);
            if (sourceRow != null) {
                Row targetRow = targetSheet.createRow(i);

                for (int j = sourceRow.getFirstCellNum(); j < sourceRow.getLastCellNum(); j++) {
                    Cell sourceCell = sourceRow.getCell(j);
                    if (sourceCell != null) {
                        Cell targetCell = targetRow.createCell(j, sourceCell.getCellType());
                        Object cellValue = getCellValue(sourceCell);
                        targetCell.setCellValue(cellValue.toString());
                    }
                }
            }
        }
    }

    private static Object getCellValue(@NotNull Cell cell) {
        return switch (cell.getCellType()) {
            case NUMERIC -> cell.getNumericCellValue();
            case STRING -> cell.getStringCellValue();
            case BOOLEAN -> cell.getBooleanCellValue();
            case BLANK -> "";
            // Handle other cell types if needed
            default -> null;
        };
    }

    public void uploadFileService(@NotNull GetUploadFileData getUploadFileData) {
        // 获取文件
        if (getUploadFileData.getFiles() != null) {
            // 获取 base64 文件流
            byte[] base64 = Base64.getDecoder().decode(String.valueOf(getUploadFileData.getFiles()));
            // 检查文件尾缀是否正确
            InputStream inputStream = new ByteArrayInputStream(base64);
            try {
                Workbook workBook = WorkbookFactory.create(inputStream);
                String getFileType = workBook.getSpreadsheetVersion().toString().toLowerCase();
                if (getFileType.contains("excel2007") || getFileType.contains("excel97") || getFileType.contains("wps")) {
                    // 文件类型正确（存储）
                    long time = new Date().getTime();
                    // 设置文件存储位置
                    String path = "./src/main/resources/excel/" + time + "/";
                    String fileName = null;
                    if (getFileType.contains("excel2007")) {
                        fileName = time + ".xlsx";
                    } else if (getFileType.contains("excel97")) {
                        fileName = time + ".xls";
                    }
                    // 创建文件
                    File file = new File(path);
                    if (!file.exists()) {
                        if (file.mkdirs()) {
                            // 成功
                            for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
                                Sheet sheet = workBook.getSheetAt(i);
                                // 检查 sheet 是否存在
                                if (!isSheetEmpty(sheet)) {
                                    if (getFileType.contains("excel2007")) {
                                        fileName = sheet.getSheetName() + ".xlsx";
                                    } else if (getFileType.contains("excel97")) {
                                        fileName = sheet.getSheetName() + ".xls";
                                    }
                                    try {
                                        Workbook targetWorkbook = WorkbookFactory.create(true);

                                        Sheet targetSheet = targetWorkbook.createSheet(sheet.getSheetName());
                                        copySheet(workBook.getSheet(sheet.getSheetName()), targetSheet);

                                        try (FileOutputStream fileOutputStream = new FileOutputStream(file + "/" + fileName)) {
                                            targetWorkbook.write(fileOutputStream);
                                            // 载入数据库
                                            ExcelInfoEntity excelInfoEntity = new ExcelInfoEntity();
                                            excelInfoEntity
                                                    .setFileName(String.valueOf(time))
                                                    .setSheetName(sheet.getSheetName())
                                                    .setType(0)
                                                    .setCreateBy(getUploadFileData.getUserid())
                                                    .setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                                            excelInfoMapper.insertExcelInfo(excelInfoEntity);
                                            returnResult = new GetFileTreeService().getFileTreeService(excelInfoMapper);
                                        }
                                    } catch (IOException | EncryptedDocumentException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        } else {
                            // 文件夹创建失败
                            returnResult = ResultUtil.error(ErrorCode.FILE_CREATION_FAILED);
                        }
                    } else {
                        // 文件存在
                        returnResult = ResultUtil.error(ErrorCode.FILE_ALREADY_EXISTS);
                    }
                } else {
                    // 文件类型不正确
                    returnResult = ResultUtil.error(ErrorCode.FILE_TYPE_IS_INCORRECT);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
