package com.ronhan.admin.generator.domain;

import lombok.Data;

/**
 * 代码生成对象
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-14 16:09
 */
@Data
public class CodeGenConfig {

    /**
     * 包名
     */
    private String packageName;
    /**
     * 作者
     */
    private String author;
    /**
     * 模块名称
     */
    private String moduleName;
    /**
     * 表前缀
     */
    private String tablePrefix;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表备注
     */
    private String comments;
}
