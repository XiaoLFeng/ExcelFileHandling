package com.wxjw.dal.pojo.data.GetFileTree;

import com.wxjw.dal.dao.ExcelInfoMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Data 自定义 GetFileTreeController 实体类构造 父亲
 *
 * @author 筱锋xiao_lfeng
 */
@Data
@AllArgsConstructor
public class GetFileTreeData {
    private String name;
    private boolean open;
    private ArrayList<Object> children;

    public GetFileTreeData setData(@NotNull ExcelInfoMapper excelInfoMapper, int id) {
        ArrayList<Object> children = new ArrayList<>();
        excelInfoMapper.getAllExcelFilesName().forEach(name -> {
            if (id == name.getType()) {
                ArrayList<Object> childrenSheet = new ArrayList<>();
                excelInfoMapper.getAllExcelInfos().forEach(sheet -> {
                    if (name.getFileName() != null && id == sheet.getType() && name.getFileName().equals(sheet.getFileName())) {
                        childrenSheet.add(new Sheet(sheet.getSheetName(), sheet.getId()));
                    }
                });
                children.add(new Children<>(name.getFileName(), name.getId(), childrenSheet));
            }
        });
        this.children = children;
        return this;
    }
}

@Data
@AllArgsConstructor
class Children<T> {
    private String name;
    private int id;
    private T children;
}

@Data
@AllArgsConstructor
class Sheet {
    private String name;
    private int id;
}
