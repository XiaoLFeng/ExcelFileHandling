package com.wxjw.service;

import com.wxjw.common.BaseResponse;
import com.wxjw.common.ResultUtil;
import com.wxjw.dal.dao.ExcelInfoMapper;
import com.wxjw.dal.pojo.ErrorCode;
import com.wxjw.dal.pojo.data.RetrieveFilesData;
import com.wxjw.dal.pojo.entity.ExcelInfoEntity;
import lombok.Getter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Service 文件查找
 *
 * @author 筱锋xiao_lfeng
 */
@Service
public class RetrieveFilesService {

    private final ExcelInfoMapper excelInfoMapper;
    @Getter
    private ResponseEntity<BaseResponse<Object>> returnResult;

    public RetrieveFilesService(ExcelInfoMapper excelInfoMapper) {
        this.excelInfoMapper = excelInfoMapper;
    }

    public void getSearchFile(@NotNull RetrieveFilesData requestBody) {
        // 检查 nodeId 是否存在
        ExcelInfoEntity getExcel = excelInfoMapper.getExcelForId(requestBody.getNodeId());
        if (getExcel != null && getExcel.getId() != null) {
            // 获取数据
            String getFileName = getExcel.getFileName();
            String getSheetName = getExcel.getSheetName();
            // 构建文件夹位置
            String filePathForXlsx = "./src/main/resources/excel/" + getFileName + "/" + getSheetName + ".xlsx";
            String filePathForXls = "./src/main/resources/excel/" + getFileName + "/" + getSheetName + ".xls";
            // 获取文件
            Workbook workbook;
            try {
                FileInputStream inputStream;
                if (Files.exists(Paths.get(filePathForXlsx))) {
                    inputStream = new FileInputStream(filePathForXlsx);
                } else {
                    inputStream = new FileInputStream(filePathForXls);
                }
                // 流输入工作目录
                workbook = new XSSFWorkbook(inputStream);
                // 关闭流
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // 进入循环配置
            // 获取单元行Row内容
            Sheet sheet = workbook.getSheetAt(0);
            ArrayList<Object[]> searchList = new ArrayList<>();
            boolean isEmpty = true;
            for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
                // 获取单元格内容
                for (int j = 0; j < sheet.getRow(i).getPhysicalNumberOfCells(); j++) {
                    // 检索Cell
                    if (sheet.getRow(i).getCell(j).getStringCellValue().contains(requestBody.getSearchConent())) {
                        searchList.add(new Object[]{sheet.getRow(i).getCell(j).getStringCellValue(), i, j});
                        isEmpty = false;
                    }
                }
            }
            if (isEmpty) {
                returnResult = ResultUtil.error(ErrorCode.RETRIEVE_EMPTY);
                return;
            }
            // 生成新 表
            Workbook newWorkbook = new XSSFWorkbook();
            Sheet newSheet = newWorkbook.createSheet("searchSheetName");
            newSheet.createRow(0).createCell(0).setCellValue("内容查找");
            newSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
            for (int i = 1; i <= searchList.size() + 1; i++) {
                if (i > 1) {
                    newSheet.createRow(i).createCell(0).setCellValue(i - 1);
                    newSheet.getRow(i).createCell(1).setCellValue(requestBody.getSearchConent());
                    newSheet.getRow(i).createCell(2).setCellValue(searchList.get(i - 2)[0].toString());
                    newSheet.getRow(i).createCell(3).setCellValue((int)searchList.get(i - 2)[1]);
                    newSheet.getRow(i).createCell(4).setCellValue((int)searchList.get(i - 2)[2]);
                } else {
                    newSheet.createRow(1).createCell(0).setCellValue("序号");
                    newSheet.getRow(1).createCell(1).setCellValue("查找");
                    newSheet.getRow(1).createCell(2).setCellValue("找到");
                    newSheet.getRow(1).createCell(3).setCellValue("Row");
                    newSheet.getRow(1).createCell(4).setCellValue("Column");
                }
            }
            // 获取命名规则
            long date = new Date().getTime();
            String newSheetName = "sheet1";
            File file = new File("./src/main/resources/excel/" + date);
            if (file.mkdirs()) {
                try (FileOutputStream outputStream = new FileOutputStream("./src/main/resources/excel/" + date + "/" + newSheetName + ".xlsx")) {
                    newWorkbook.write(outputStream);
                    // 插入数据库
                    ExcelInfoEntity excelInfoEntity = new ExcelInfoEntity();
                    excelInfoEntity
                            .setFileName(String.valueOf(date))
                            .setSheetName(newSheetName)
                            .setType(2)
                            .setParentId(getExcel.getId())
                            .setCreateBy(getExcel.getCreateBy())
                            .setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    // 数据库写入
                    excelInfoMapper.insertExcelInfo(excelInfoEntity);
                    // 返回结果
                    returnResult = ResultUtil.success();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            // 为空操作
            returnResult = ResultUtil.error(ErrorCode.THERE_IS_NO_SUCH_RECORD);
        }
    }
}
