package com.ronhan.admin.generator.controller;

import com.ronhan.admin.common.utils.R;
import com.ronhan.admin.generator.domain.CodeGenConfig;
import com.ronhan.admin.generator.service.SysCodeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 系统代码生成控制器
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-14 15:56
 */
@RestController
@RequestMapping("/codegen")
public class SysCodeGenController {

    @Resource
    private SysCodeService sysCodeService;

    /**
     * 获取数据库的所有表
     *
     * @param tableSchema 库名
     * @return R
     */
    @GetMapping("/getTableList")
    public R getTableList(@RequestParam String tableSchema) {
        return R.ok(sysCodeService.findTableList(tableSchema));
    }

    /**
     * 获取表中的所有字段以及列属性
     * @param tableName 表名
     * @param tableSchema 库名
     * @return R
     */
    @GetMapping("/getTableColumnList")
    public R getTableColumnList(@RequestParam String tableName, @RequestParam String tableSchema) {
        return R.ok(sysCodeService.findColumnList(tableName, tableSchema));
    }

//    @PreAuthorize("hasAuthority('sys:codegen:codegen') or hasRole('ADMIN')")
    @PostMapping("/codegen")
    public R generatorCode(@RequestBody CodeGenConfig codeGenConfig){
        return R.ok(sysCodeService.generatorCode(codeGenConfig));
    }
}
