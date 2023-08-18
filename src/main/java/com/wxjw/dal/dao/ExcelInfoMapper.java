package com.wxjw.dal.dao;

import com.wxjw.dal.pojo.entity.ExcelInfoEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

/**
 * Mapper 数据库相关 接口
 *
 * @author 筱锋xiao_lfeng
 */
@Mapper
@ComponentScan
public interface ExcelInfoMapper {

    /**
     * @return 返回全部的数据（不加以修饰）
     */
    @Select("SELECT * FROM excel_file_handling.excel_info")
    List<ExcelInfoEntity> getAllExcelInfos();

    /**
     * @return 返回全部的文件名称（去重）
     */
    @Select("SELECT t1.* FROM excel_file_handling.excel_info t1 LEFT JOIN excel_file_handling.excel_info t2 ON t1.file_name = t2.file_name AND t1.id > t2.id WHERE t2.id IS NULL")
    List<ExcelInfoEntity> getAllExcelFilesName();

    /**
     * @return 返回全部的文件名称（不去重）
     */
    @Select("SELECT * FROM excel_file_handling.excel_info WHERE file_name = #{fileName}")
    List<ExcelInfoEntity> getAllExcelFilesNameNoRepetition(String fileName);


    /**
     * @param id 序号
     * @return 返回单条数据
     */
    @Select("SELECT * FROM excel_file_handling.excel_info WHERE id = #{id}")
    ExcelInfoEntity getExcelForId(int id);

    /**
     * @param id 序号
     * @return 是否删除成功
     */
    @Delete("DELETE FROM excel_file_handling.excel_info WHERE id = #{id}")
    boolean deleteExcelForId(int id);

    @Update("UPDATE excel_file_handling.excel_info SET file_name = #{fileName}, sheet_name = #{sheetName}, table_name = #{tableName}, type = #{type}, create_by = #{createBy}, create_time = #{createTime}, update_by = #{updateBy}, update_time = #{updateTime} WHERE id = #{id}")
    boolean updateExcelInfo(ExcelInfoEntity excelInfoEntity);

    /**
     * @param excelInfoEntity excel
     * @return 是否插入成功
     */
    @Insert("INSERT INTO excel_file_handling.excel_info (`parent_id`,`file_name`,`sheet_name`,`table_name`,`type`,`create_by`,`create_time`,`update_by`,`update_time`) VALUES (#{parentId}, #{fileName}, #{sheetName}, #{tableName}, #{type}, #{createBy}, #{createTime}, #{updateBy}, #{updateTime})")
    boolean insertExcelInfo(ExcelInfoEntity excelInfoEntity);
}
