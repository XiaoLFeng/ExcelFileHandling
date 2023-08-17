package com.wxjw.service;

import com.wxjw.common.BaseResponse;
import com.wxjw.common.ResultUtil;
import com.wxjw.dal.dao.ExcelInfoMapper;
import com.wxjw.dal.pojo.ErrorCode;
import com.wxjw.dal.pojo.data.getFileTree.GetFileTreeData;
import com.wxjw.dal.pojo.entity.ExcelInfoEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Service GetFileTree逻辑服务处理
 *
 * @author 筱锋xiao_lfeng
 */

@Service
public class GetFileTreeService {

    public ResponseEntity<BaseResponse<Object>> getFileTreeService(ExcelInfoMapper excelInfoMapper) {
        // 从数据库读取数据
        ArrayList<ExcelInfoEntity> allExcelFileName = (ArrayList<ExcelInfoEntity>) excelInfoMapper.getAllExcelFilesName();
        // 检查所得数据是否为空
        if (!allExcelFileName.isEmpty() && !excelInfoMapper.getAllExcelInfos().isEmpty()) {
            ArrayList<Object> data = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                switch (i) {
                    case 0 -> data.add(new GetFileTreeData("公共库", true, null).setData(excelInfoMapper, i));
                    case 1 -> data.add(new GetFileTreeData("高级库", true, null).setData(excelInfoMapper, i));
                    case 2 -> data.add(new GetFileTreeData("个人库", true, null).setData(excelInfoMapper, i));
                }
            }
            return ResultUtil.success(data, "输出成功");
        } else {
            return ResultUtil.error(ErrorCode.DATA_IS_EMPTY);
        }
    }
}
