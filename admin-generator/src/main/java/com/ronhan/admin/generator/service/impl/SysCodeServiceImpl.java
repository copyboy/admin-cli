package com.ronhan.admin.generator.service.impl;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.ronhan.admin.generator.domain.CodeGenConfig;
import com.ronhan.admin.generator.domain.SysColumnEntity;
import com.ronhan.admin.generator.domain.SysTableEntity;
import com.ronhan.admin.generator.mapper.SysCodeMapper;
import com.ronhan.admin.generator.service.SysCodeService;
import com.ronhan.admin.generator.util.CodeGenUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 代码生成服务实现类
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-14 16:15
 */
@Service
public class SysCodeServiceImpl implements SysCodeService {

    @Resource
    private SysCodeMapper sysCodeMapper;

    @Override
    public List<SysTableEntity> findTableList(String tableSchema) {
        return sysCodeMapper.findTableList(tableSchema);
    }

    @Override
    public List<SysColumnEntity> findColumnList(String tableName, String tableSchema) {
        return sysCodeMapper.findColumnList(tableName, tableSchema);
    }

    @Override
    public boolean generatorCode(CodeGenConfig codeGenConfig) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setDbType(DbType.MYSQL)
                .setUrl("url")
                .setUsername("username")
                .setPassword("password")
                .setDriverName("driverName");
        CodeGenUtil codeGenUtil = new CodeGenUtil();
        codeGenUtil.generateByTables(dataSourceConfig, codeGenConfig.getPackageName(),
                codeGenConfig.getAuthor(), codeGenConfig.getModuleName(), codeGenConfig.getTableName());
        return true;
    }
}
