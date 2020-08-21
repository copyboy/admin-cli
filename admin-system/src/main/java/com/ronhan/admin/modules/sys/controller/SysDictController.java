package com.ronhan.admin.modules.sys.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ronhan.admin.common.utils.R;
import com.ronhan.admin.log.annotation.SysOperaLog;
import com.ronhan.admin.modules.sys.domain.SysDict;
import com.ronhan.admin.modules.sys.dto.DictDTO;
import com.ronhan.admin.modules.sys.service.ISysDictService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
@RestController
@RequestMapping("/dict")
public class SysDictController {

    @Resource
    private ISysDictService dictService;

    /**
     * 添加字典信息
     */
    @SysOperaLog(description = "添加字典信息")
    @PreAuthorize("hasAuthority('sys:dict:add')")
    @PostMapping
    public R add(@RequestBody SysDict sysDict) {
        return R.ok(dictService.save(sysDict));
    }

    /**
     * 获取字典列表集合
     */
    @SysOperaLog(description = "查询字典集合")
    @GetMapping
    @PreAuthorize("hasAuthority('sys:dipt:view')")
    public R getList(Page<SysDict> page, SysDict sysDict) {
        return R.ok(dictService.page(page, Wrappers.query(sysDict)));
    }

    /**
     * 更新字典
     */
    @SysOperaLog(description = "更新字典")
    @PreAuthorize("hasAuthority('sys:dict:edit')")
    @PutMapping
    public R update(@RequestBody DictDTO dictDto) {
        return R.ok(dictService.updateDict(dictDto));
    }

    /**
     * 根据id删除字典
     */
    @SysOperaLog(description = "根据id删除字典")
    @PreAuthorize("hasAuthority('sys:dict:del')")
    @DeleteMapping("{id}")
    public R delete(@PathVariable("id") int id) {
        return R.ok(dictService.removeById(id));
    }


    /**
     * 根据字典名称查询字段详情
     */
    @GetMapping("/queryDictItemByDictName/{dictName}")
    public R queryDictItemByDictName(@PathVariable("dictName") String dictName) {
        return R.ok(dictService.queryDictItemByDictName(dictName));
    }

}

