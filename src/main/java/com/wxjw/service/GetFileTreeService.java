package com.wxjw.service;

import com.wxjw.common.Result;
import com.wxjw.dal.dao.ExcelInfoMapper;
import com.wxjw.dal.pojo.data.getFileTree.GetFileTreeChildren;
import com.wxjw.dal.pojo.data.getFileTree.GetFileTreeFather;
import com.wxjw.dal.pojo.data.getFileTree.GetFileTreeSheet;
import com.wxjw.dal.pojo.entity.ExcelInfoEntity;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Service GetFileTree逻辑服务处理
 *
 * @author 筱锋xiao_lfeng
 */

@Service
public class GetFileTreeService {

    public Result<Object> getFileTreeService(ExcelInfoMapper excelInfoMapper) {
        // 从数据库读取数据
        ArrayList<ExcelInfoEntity> allExcelFileName = (ArrayList<ExcelInfoEntity>) excelInfoMapper.getAllExcelFilesName();
        // 检查所得数据是否为空
        if (!allExcelFileName.isEmpty() && !excelInfoMapper.getAllExcelInfos().isEmpty()) {
            ArrayList<Object> data = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                // 循环创建
                ArrayList<Object> fatherList = new ArrayList<>();
                for (ExcelInfoEntity excelInfoEntityFather : allExcelFileName) {
                    if (excelInfoEntityFather.getType() == i) {
                        ArrayList<Object> childrenList = new ArrayList<>();
                        for (ExcelInfoEntity excelInfoEntityChildren : excelInfoMapper.getAllExcelInfos()) {
                            GetFileTreeSheet getFileTreeSheet;
                            getFileTreeSheet = new GetFileTreeSheet(excelInfoEntityChildren.getSheetName(), excelInfoEntityChildren.getId());
                            childrenList.add(getFileTreeSheet);
                        }


                        GetFileTreeChildren<Object> getFileTreeChildren;
                        getFileTreeChildren = new GetFileTreeChildren<>(excelInfoEntityFather.getFileName(), excelInfoEntityFather.getId(), childrenList);
                        fatherList.add(getFileTreeChildren);
                    }
                }
                switch (i) {
                    case 0 -> data.add(new GetFileTreeFather<>("公共库", true, fatherList));
                    case 1 -> data.add(new GetFileTreeFather<>("高级库", true, fatherList));
                    case 2 -> data.add(new GetFileTreeFather<>("个人库", true, fatherList));
                }
            }
            return new Result<>(200, data, "输出成功");
        } else {
            return new Result<>(403, null, "数据为空");
        }
    }
}
