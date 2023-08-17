package com.wxjw.dal.pojo.data.GetSheet;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data 获取
 *
 * @author 筱锋xiao_lfeng
 */
@Data
@AllArgsConstructor
public class GetSheetData<T> {
    private T title;
    private T tablehead;
    private T colwidth;
    private T tabledata;
}
