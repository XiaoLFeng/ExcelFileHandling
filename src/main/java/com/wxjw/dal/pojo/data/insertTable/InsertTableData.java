package com.wxjw.dal.pojo.data.insertTable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lfeng
 */
@Data
@AllArgsConstructor
public class InsertTableData {
    private String action;
    private long id;
    private long paren_id;
    private String file_name;
    private String sheet_name;
    private String table_name;
    private int type;
    private String create_by;
    private String create_time;
    private String update_by;
    private String update_time;
}
