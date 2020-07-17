package com.ronhan.admin.generator.mapper;

import com.ronhan.admin.generator.domain.SysColumnEntity;
import com.ronhan.admin.generator.domain.SysTableEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 表结构属性查询 Mapper
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-14 16:09
 */
@Repository
public interface SysCodeMapper {

    /**
     * 根据数据库名称查询所有表属性
     */
    @Select("SELECT TABLE_NAME AS tableName,TABLE_COMMENT AS comments,TABLE_SCHEMA AS tableSchema,CREATE_TIME AS createTime FROM information_schema.TABLES WHERE table_schema=#{tableSchema} ORDER BY createTime DESC")
    List<SysTableEntity> findTableList(String tableSchema);

    /**
     * 根据数据库名和表名查询表的列属性
     */
    @Select("select COLUMN_NAME AS columnName,DATA_TYPE AS dataType,COLUMN_COMMENT AS columnComment,CHARACTER_SET_NAME AS characterSetName,COLUMN_TYPE AS columnType from information_schema.COLUMNS where table_name = #{tableName} and table_schema = #{tableSchema}")
    List<SysColumnEntity> findColumnList(String tableName, String tableSchema);
}
