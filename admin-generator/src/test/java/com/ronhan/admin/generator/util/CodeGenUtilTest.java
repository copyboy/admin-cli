package com.ronhan.admin.generator.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.ronhan.admin.generator.domain.CodeGenConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 代码生成测试类
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-17 11:39
 */
class CodeGenUtilTest {

    @Test
    void generateByTables() {

        CodeGenConfig codeGenConfig = new CodeGenConfig();
        codeGenConfig.setAuthor("qingdong.zhang");
        codeGenConfig.setComments("comments");
        codeGenConfig.setModuleName("generator");
        codeGenConfig.setTableName("sys_log");
        codeGenConfig.setTablePrefix("");
        codeGenConfig.setPackageName("com.ronhan.admin.generator.tmp");

        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setDbType(DbType.MYSQL)
                .setUrl("jdbc:mysql://127.0.0.1:3306/ronhan?serverTimezone=UTC")
                .setUsername("root")
                .setPassword("root")
                .setDriverName("com.mysql.cj.jdbc.Driver");
        CodeGenUtil codeGenUtil = new CodeGenUtil();
        codeGenUtil.generateByTables(dataSourceConfig, codeGenConfig.getPackageName(),
                codeGenConfig.getAuthor(), codeGenConfig.getModuleName(), codeGenConfig.getTableName());

    }
}