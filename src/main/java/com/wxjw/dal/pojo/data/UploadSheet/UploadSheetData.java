package com.wxjw.dal.pojo.data.UploadSheet;

import lombok.Getter;

import java.util.ArrayList;

/**
 * Data 处理前端获取内容
 * @author 筱锋xiao_lfeng
 */
@Getter
public class UploadSheetData<T> {
    private String action;
    private int nodeId;
    private ArrayList<ArrayList<T>> cellInfo;
}
