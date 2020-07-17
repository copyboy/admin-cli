package com.ronhan.admin.generator.service;

import com.ronhan.admin.generator.domain.CodeGenConfig;
import com.ronhan.admin.generator.domain.SysColumnEntity;
import com.ronhan.admin.generator.domain.SysTableEntity;

import java.util.List;

/**
 * 代码生成服务
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-14 16:14
 */
public interface SysCodeService {

    List<SysTableEntity> findTableList(String tableSchema);

    List<SysColumnEntity> findColumnList(String tableName, String tableSchema);

    /**
     * 代码生成
     */
    boolean generatorCode(CodeGenConfig codeGenConfig);

}
