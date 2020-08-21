package com.ronhan.admin.modules.sys.controller;


import com.ronhan.admin.common.utils.R;
import com.ronhan.admin.log.annotation.SysOperaLog;
import com.ronhan.admin.modules.sys.domain.SysMenu;
import com.ronhan.admin.modules.sys.dto.MenuDTO;
import com.ronhan.admin.modules.sys.service.ISysMenuService;
import com.ronhan.admin.modules.sys.util.AdminUtil;
import com.ronhan.admin.security.SecurityUser;
import com.ronhan.admin.security.util.SecurityUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController {

    @Resource
    private ISysMenuService menuService;

    /**
     * 添加菜单
     */
    @PreAuthorize("hasAuthority('sys:menu:add')")
    @SysOperaLog(description = "添加菜单")
    @PostMapping
    public R save(@RequestBody SysMenu menu) {
        return R.ok(menuService.save(menu));
    }

    /**
     * 修改菜单
     */
    @PreAuthorize("hasAuthority('sys:menu:update')")
    @SysOperaLog(description = "修改菜单")
    @PutMapping
    public R updateMenu(@RequestBody MenuDTO menuDto) {
        return R.ok(menuService.updateMenuById(menuDto));
    }

    /**
     * 根据id删除菜单
     */
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @SysOperaLog(description = "删除菜单")
    @DeleteMapping("/{id}")
    public R deleteMenu(@PathVariable("id") Integer id) {
        return menuService.removeMenuById(id);
    }

    /**
     * 获取菜单树
     */
    @GetMapping
    public R getMenuTree() {
        SecurityUser securityUser = SecurityUtil.getUser();
        return R.ok(menuService.selectMenuTree(securityUser.getUserId()));
    }

    /**
     * 获取所有菜单
     */
    @GetMapping("/getMenus")
    public R getMenus() {
        return R.ok(menuService.selectMenuTree(0));
    }

    /**
     * 获取路由
     */
    @GetMapping("/getRouters")
    public R getRouters() {
        SecurityUser securityUser = SecurityUtil.getUser();
        return R.ok(AdminUtil.buildMenus(menuService.selectMenuTree(securityUser.getUserId())));
    }

}

