package com.wxjw.dal.pojo.data.getFileTree;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data 自定义 GetFileTreeController 实体类构造 父亲
 *
 * @author 筱锋xiao_lfeng
 */
@Data
@AllArgsConstructor
public class GetFileTreeFather<T> {
    private String name;
    private boolean open;
    private T children;
}
