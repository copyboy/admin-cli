package com.ronhan.admin.modules.sys.controller;


import com.ronhan.admin.common.utils.R;
import com.ronhan.admin.modules.sys.service.ISysRoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Resource
    private ISysRoleService roleService;

    /**
     * 获取角色列表
     */
    @GetMapping
    @PreAuthorize("hasAuthority('sys:role:view')")
    public R getRoleList(@RequestParam String roleName) {
        return R.ok(roleService.selectRoleList(roleName));
    }
}

