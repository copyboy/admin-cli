package com.ronhan.admin.modules.sys.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ronhan.admin.common.utils.R;
import com.ronhan.admin.log.annotation.SysOperaLog;
import com.ronhan.admin.modules.sys.domain.SysUser;
import com.ronhan.admin.modules.sys.dto.UserDTO;
import com.ronhan.admin.modules.sys.service.ISysUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Resource
    private ISysUserService userService;


    /**
     * 获取用户列表集合
     */
    @SysOperaLog(description = "查询用户集合")
    @GetMapping
    @PreAuthorize("hasAuthority('sys:user:view')")
    public R getList(Page<SysUser> page, UserDTO userDTO) {
        return R.ok(userService.getUsersWithRolePage(page, userDTO));
    }


}

