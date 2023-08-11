package com.wxjw.dal.pojo.entity;

import lombok.Data;
import org.springframework.lang.Nullable;

/**
 * Entity Excel数据表实体类
 *
 * @author 筱锋xiao_lfeng
 */
@Data
public class ExcelInfoEntity {
    private int id;
    private int parentId;
    @Nullable private String fileName;
    @Nullable private String sheetName;
    @Nullable private String tableName;
    private int type;
    @Nullable private String createBy;
    @Nullable private String createTime;
    @Nullable private String updateBy;
    @Nullable private String updateTime;
}
