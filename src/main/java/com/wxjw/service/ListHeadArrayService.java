package com.wxjw.service;

import com.wxjw.common.BaseResponse;
import com.wxjw.common.ResultUtil;
import com.wxjw.dal.dao.ExcelInfoMapper;
import com.wxjw.dal.pojo.ErrorCode;
import com.wxjw.dal.pojo.data.ListHeadArray.ListHeadArrayData;
import com.wxjw.dal.pojo.entity.ExcelInfoEntity;
import lombok.Getter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Service 列表获取控制器
 *
 * @author 筱锋xiao_lfeng
 */
@Service
public class ListHeadArrayService {

    private final ExcelInfoMapper excelInfoMapper;
    @Getter
    private ResponseEntity<BaseResponse<Object>> returnResult;

    public ListHeadArrayService(ExcelInfoMapper excelInfoMapper) {
        this.excelInfoMapper = excelInfoMapper;
    }

    public void getListHeadArray(@NotNull ListHeadArrayData requestBody) {
         // 检查数据是否存在
        String number = Arrays.stream(requestBody.getNodeId())
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(","));
        ArrayList<ExcelInfoEntity> excelInfo = (ArrayList<ExcelInfoEntity>) excelInfoMapper.getExcelForIds(number);
        ArrayList<Object> excelFileIds = new ArrayList<>();
        if (excelInfo != null && !excelInfo.isEmpty()) {
            for (ExcelInfoEntity excelInfoEntity : excelInfo) {
                // 获取文件信息
                String fileName = excelInfoEntity.getFileName();
                String sheetName = excelInfoEntity.getSheetName();
                // 获取文件路径
                String filePathForXlsx = "./src/main/resources/excel/" + fileName + "/" + sheetName + ".xlsx";
                String filePathForXls = "./src/main/resources/excel/" + fileName + "/" + sheetName + ".xls";
                // 读取文件
                Workbook workbook;
                try {
                    FileInputStream fileInputStream;
                    if (Files.exists(Path.of(filePathForXlsx))) {
                        fileInputStream = new FileInputStream(filePathForXlsx);
                    } else {
                        fileInputStream = new FileInputStream(filePathForXls);
                    }
                    // 获取文件内容
                    workbook = new XSSFWorkbook(fileInputStream);
                    // 关闭流
                    fileInputStream.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                // 打开文件获取内容
                Sheet sheet = workbook.getSheetAt(0);
                // 获取标题抬头
                ArrayList<Object> data = new ArrayList<>();
                for (int i = 0; i < sheet.getRow(1).getPhysicalNumberOfCells(); i++) {
                    switch (sheet.getRow(1).getCell(i).getCellType()) {
                        case NUMERIC -> data.add(sheet.getRow(1).getCell(i).getNumericCellValue());
                        case BOOLEAN -> data.add(sheet.getRow(1).getCell(i).getBooleanCellValue());
                        case FORMULA -> data.add(sheet.getRow(1).getCell(i).getCellFormula());
                        default -> data.add(sheet.getRow(1).getCell(i).getStringCellValue());
                    }
                }
                excelFileIds.add(data);
            }
            // 处理完成返回结果集
            returnResult = ResultUtil.success(excelFileIds,"输出成功");
        } else {
            returnResult = ResultUtil.error(ErrorCode.DATA_IS_EMPTY);
        }
    }
}
