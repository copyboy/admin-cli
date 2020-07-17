package com.ronhan.admin.generator.domain;

import lombok.Data;

/**
 * 数据库列名属性表
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-14 16:09
 * select COLUMN_NAME,DATA_TYPE,COLUMN_COMMENT,CHARACTER_SET_NAME,COLUMN_TYPE from information_schema.COLUMNS where table_name = #{tableName} and table_schema = #{tableSchema}
 */
@Data
public class SysColumnEntity {

    /**
     * 列表
     */
    private String columnName;
    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 备注
     */
    private String columnComment;
    /**
     * 字符集
     */
    private String characterSetName;
    /**
     * 列字段类型
     */
    private String columnType;

}
