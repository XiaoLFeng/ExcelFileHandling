package com.wxjw.dal.dao;

import com.wxjw.dal.pojo.entity.ExcelInfoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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
    @Select("SELECT t1.* FROM excel_file_handling.excel_info t1 LEFT JOIN excel_file_handling.excel_info t2 ON t1.file_name = t2.file_name AND t1.id < t2.id WHERE t2.id")
    List<ExcelInfoEntity> getAllExcelFilesName();


    /**
     * @param id         主键
     * @param parentId   父级 ID
     * @param fileName   文件名称
     * @param sheetName  sheet 名称
     * @param tableName  表名称
     * @param type       类型
     * @param createBy   创建者
     * @param createTime 创建时间
     * @param updateBy   更新者
     * @param updateTime 更新时间
     * @return 是否插入成功
     */
    @Insert("INSERT INTO excel_file_handling.excel_info (`id`,`parent_id`,`file_name`,`sheet_name`,`table_name`,`type`,`create_by`,`create_time`,`update_by`,`update_time`) VALUES (#{id}, #{parentId},#{fileName},#{sheetName},#{tableName},#{type},#{createBy}, #{createTime},#{updateBy},#{updateTime})")
    boolean insertExcelInfo(int id, int parentId, String fileName, String sheetName, String tableName, int type, String createBy, String createTime, String updateBy, String updateTime);
}
