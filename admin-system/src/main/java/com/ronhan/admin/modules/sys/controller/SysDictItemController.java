package com.ronhan.admin.modules.sys.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ronhan.admin.common.utils.R;
import com.ronhan.admin.log.annotation.SysOperaLog;
import com.ronhan.admin.modules.sys.domain.SysDictItem;
import com.ronhan.admin.modules.sys.service.ISysDictItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 字典详情表 前端控制器
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
@RestController
@RequestMapping("/dictItem")
public class SysDictItemController {

    @Resource
    private ISysDictItemService dictItemService;

    /**
     * 分页查询字典详情内容
     * @param page 分页对象
     */
    @SysOperaLog(description = "查询字典详情集合")
    @GetMapping
    public R getDictItemPage(Page<SysDictItem> page, SysDictItem sysDictItem) {
        return R.ok(dictItemService.page(page, Wrappers.query(sysDictItem)));
    }

    /**
     * 添加字典详情
     */
    @SysOperaLog(description = "添加字典详情")
    @PreAuthorize("hasAuthority('sys:dictItem:add')")
    @PostMapping
    public R add(@RequestBody SysDictItem sysDictItem) {
        return R.ok(dictItemService.save(sysDictItem));
    }

    /**
     * 更新字典详情
     */
    @SysOperaLog(description = "更新字典详情")
    @PreAuthorize("hasAuthority('sys:dictItem:edit')")
    @PutMapping
    public R update(@RequestBody SysDictItem sysDictItem) {
        return R.ok(dictItemService.updateById(sysDictItem));
    }

    /**
     * 删除字典详情
     */
    @SysOperaLog(description = "删除字典详情")
    @PreAuthorize("hasAuthority('sys:dictItem:del')")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") String id) {
        return R.ok(dictItemService.removeById(id));
    }

}

