package com.wxjw.service;

import com.wxjw.common.BaseResponse;
import com.wxjw.common.ResultUtil;
import com.wxjw.dal.dao.ExcelInfoMapper;
import com.wxjw.dal.pojo.ErrorCode;
import com.wxjw.dal.pojo.data.GetSheet.GetSheetData;
import com.wxjw.dal.pojo.data.GetSheet.GetSheetResultBody;
import com.wxjw.dal.pojo.entity.ExcelInfoEntity;
import lombok.Getter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Service 获取 Sheet
 *
 * @author 筱锋xiao_lfeng
 */
@Service
public class GetSheetService {

    private final ExcelInfoMapper excelInfoMapper;
    @Getter
    private ResponseEntity<BaseResponse<Object>> returnResult;

    public GetSheetService(ExcelInfoMapper excelInfoMapper) {
        this.excelInfoMapper = excelInfoMapper;
    }

    public void getSheet(@NotNull GetSheetResultBody resultBody) {
        ExcelInfoEntity getSheet = excelInfoMapper.getExcelForId(resultBody.getId());
        if (getSheet != null) {
            // 获取文件所在地址
            String filePath = getSheet.getFileName();
            String fileName = getSheet.getSheetName();
            // 文件地址
            String pathXlsx = "./src/main/resources/excel/" + filePath + "/" + fileName + ".xlsx";
            String pathXls = "./src/main/resources/excel/" + filePath + "/" + fileName + ".xls";
            // 文件输入解析
            try {
                InputStream inputStream;
                if (Files.exists(Paths.get(pathXlsx))) {
                    inputStream = new FileInputStream(pathXlsx);
                } else {
                    inputStream = new FileInputStream(pathXls);
                }
                Sheet getEntitySheet = new XSSFWorkbook(inputStream).getSheetAt(0);
                inputStream.close();
                // 获取 title
                Row titleRow = getEntitySheet.getRow(0);
                StringBuilder title = null;
                Iterator<Cell> iterator = titleRow.cellIterator();
                while (iterator.hasNext()) {
                    Cell cell = iterator.next();
                    title = (title == null || title.isEmpty() ? new StringBuilder() : title).append(cell.toString());
                }
                // 获取 Header
                Iterator<Cell> headerRow = getEntitySheet.getRow(1).cellIterator();
                // 获取总行数
                ArrayList<ArrayList<Object>> line = new ArrayList<>();
                String[] rowMax = new String[getEntitySheet.getRow(1).getPhysicalNumberOfCells()];
                ArrayList<String> headerArray = null;
                for (int i = 2; i < getEntitySheet.getPhysicalNumberOfRows(); i++) {
                    Row loopRow = getEntitySheet.getRow(i);
                    ArrayList<Object> temp = new ArrayList<>();
                    // 验证Cell并判断所有 Row 中数字最大的
                    headerArray = new ArrayList<>();
                    for (int j = 0; getEntitySheet.getRow(1).cellIterator().hasNext(); j++) {
                        if (j < getEntitySheet.getRow(1).getPhysicalNumberOfCells()) {
                            try {
                                if (rowMax[j] == null || rowMax[j].length() < loopRow.getCell(j).toString().length()) {
                                    switch (loopRow.getCell(j).getCellType()) {
                                        case NUMERIC -> rowMax[j] = String.valueOf(loopRow.getCell(j).getNumericCellValue());
                                        case BOOLEAN -> rowMax[j] = String.valueOf(loopRow.getCell(j).getBooleanCellValue());
                                        case BLANK -> {
                                            continue;
                                        }
                                        case ERROR -> rowMax[j] = String.valueOf(loopRow.getCell(j).getErrorCellValue());
                                        default -> rowMax[j] = String.valueOf(loopRow.getCell(j).getStringCellValue());
                                    }
                                }
                            } catch (NullPointerException e) {
                                continue;
                            }
                            headerArray.add(String.valueOf(getEntitySheet.getRow(1).getCell(j).getStringCellValue()));
                            temp.add(String.valueOf(loopRow.getCell(j)));
                        } else {
                            break;
                        }
                    }
                    line.add(temp);
                }
                // 整理数据
                returnResult = ResultUtil.success(new GetSheetData<>(title, headerArray, rowMax, line));
                // 获取
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // 为空操作
            returnResult = ResultUtil.error(ErrorCode.THERE_IS_NO_SUCH_RECORD);
        }
    }
}
