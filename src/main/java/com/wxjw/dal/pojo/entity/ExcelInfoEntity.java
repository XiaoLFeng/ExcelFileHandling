package com.wxjw.dal.pojo.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.lang.Nullable;

/**
 * Entity Excel数据表实体类
 *
 * @author 筱锋xiao_lfeng
 */
@Data
@Accessors(chain = true)
public class ExcelInfoEntity {
    @Nullable
    private Integer id;
    @Nullable
    private Integer parentId;
    @Nullable
    private String fileName;
    @Nullable
    private String sheetName;
    @Nullable
    private String tableName;
    private int type;
    @Nullable
    private String createBy;
    @Nullable
    private String createTime;
    @Nullable
    private String updateBy;
    @Nullable
    private String updateTime;
}
