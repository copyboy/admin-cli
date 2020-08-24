package com.ronhan.admin.modules.sys.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ronhan.admin.common.utils.R;
import com.ronhan.admin.log.annotation.SysOperaLog;
import com.ronhan.admin.modules.sys.domain.SysTenant;
import com.ronhan.admin.modules.sys.service.ISysTenantService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 租户表 前端控制器
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
@RestController
@RequestMapping("/tenant")
public class SysTenantController {

    @Resource
    private ISysTenantService sysTenantService;

    /**
     * 分页查询
     * @param page      分页对象
     * @param sysTenant 租户对象
     */
    @GetMapping("/page")
    public R getSysTenantPage(Page<SysTenant> page, SysTenant sysTenant) {
        return R.ok(sysTenantService.page(page, Wrappers.query(sysTenant)));
    }

    /**
     * 查询全部有效的租户
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(sysTenantService.getNormalTenant());
    }


    /**
     * 新增租户
     */
    @SysOperaLog(description = "新增租户")
    @PostMapping
    @PreAuthorize("hasAuthority('sys:tenant:add')")
    public R save(@RequestBody SysTenant sysTenant) {
        return R.ok(sysTenantService.saveTenant(sysTenant));
    }

    /**
     * 修改租户
     */
    @SysOperaLog(description = "修改租户")
    @PutMapping
    @PreAuthorize("hasAuthority('sys:tenant:update')")
    public R updateById(@RequestBody SysTenant sysTenant) {
        return R.ok(sysTenantService.updateById(sysTenant));
    }


    /**
     * 通过id删除租户
     */
    @SysOperaLog(description = "删除租户")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:tenant:del')")
    public R removeById(@PathVariable Integer id) {
        return R.ok(sysTenantService.removeById(id));
    }

}

