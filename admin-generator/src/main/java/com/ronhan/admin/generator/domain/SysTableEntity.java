package com.ronhan.admin.generator.domain;

import lombok.Data;

/**
 * 数据库表属性
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-14 16:09
 *SELECT TABLE_NAME,TABLE_COMMENT,TABLE_SCHEMA,CREATE_TIME FROM information_schema.TABLES WHERE table_schema='test';
 */
@Data
public class SysTableEntity {

    /**
     * 名称
     */
    private String tableName;
    /**
     * 备注
     */
    private String comments;
    /**
     * 归属库
     */
    private String tableSchema;
    /**
     * 创建时间
     */
    private String createTime;
}
