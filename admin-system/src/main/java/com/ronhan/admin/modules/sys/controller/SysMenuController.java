package com.ronhan.admin.modules.sys.controller;


import com.ronhan.admin.common.utils.R;
import com.ronhan.admin.modules.sys.service.ISysMenuService;
import com.ronhan.admin.modules.sys.util.AdminUtil;
import com.ronhan.admin.security.SecurityUser;
import com.ronhan.admin.security.util.SecurityUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 获取路由
     */
    @GetMapping("/getRouters")
    public R getRouters() {
        SecurityUser securityUser = SecurityUtil.getUser();
        return R.ok(AdminUtil.buildMenus(menuService.selectMenuTree(securityUser.getUserId())));
    }

}

