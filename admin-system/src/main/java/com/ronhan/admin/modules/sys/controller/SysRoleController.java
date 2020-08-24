package com.ronhan.admin.modules.sys.controller;


import com.ronhan.admin.common.utils.R;
import com.ronhan.admin.log.annotation.SysOperaLog;
import com.ronhan.admin.modules.sys.dto.RoleDTO;
import com.ronhan.admin.modules.sys.service.ISysRoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 保存角色以及菜单权限
     */
    @SysOperaLog(description = "保存角色以及菜单权限")
    @PostMapping
    @PreAuthorize("hasAuthority('sys:role:add')")
    public R save(@RequestBody RoleDTO roleDto) {
        return R.ok(roleService.saveRoleMenu(roleDto));
    }

    /**
     * 根据角色id获取菜单
     */
    @SysOperaLog(description = "据角色id获取菜单")
    @GetMapping("/findRoleMenus/{roleId}")
    public R findRoleMenus(@PathVariable("roleId") Integer roleId) {
        return R.ok(roleService.findMenuListByRoleId(roleId));
    }

    /**
     * 更新角色以及菜单权限
     */
    @SysOperaLog(description = "更新角色以及菜单权限")
    @PutMapping
    @PreAuthorize("hasAuthority('sys:role:update')")
    public R update(@RequestBody RoleDTO roleDto) {
        return R.ok(roleService.updateRoleMenu(roleDto));
    }

    /**
     * 删除角色以及权限
     */
    @SysOperaLog(description = "删除角色以及权限")
    @DeleteMapping("/{roleId}")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public R delete(@PathVariable("roleId") Integer roleId) {
        return R.ok(roleService.removeById(roleId));
    }


}

