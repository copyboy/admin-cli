package com.ronhan.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ronhan.admin.modules.sys.domain.SysMenu;
import com.ronhan.admin.modules.sys.mapper.SysMenuMapper;
import com.ronhan.admin.modules.sys.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ronhan.admin.modules.sys.service.ISysRoleMenuService;
import com.ronhan.admin.modules.sys.util.AdminUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Resource
    private ISysRoleMenuService roleMenuService;

    @Override
    public List<String> findPermsByUserId(Integer userId) {
        return baseMapper.findPermsByUserId(userId);
    }

    @Override
    public List<SysMenu> selectMenuTree(Integer uid) {

        LambdaQueryWrapper<SysMenu> sysMenuLambdaQueryWrapper = Wrappers.<SysMenu>query().lambda();
        sysMenuLambdaQueryWrapper.select(SysMenu::getMenuId, SysMenu::getName, SysMenu::getPerms, SysMenu::getPath, SysMenu::getParentId, SysMenu::getComponent, SysMenu::getIsFrame, SysMenu::getIcon, SysMenu::getSort, SysMenu::getType, SysMenu::getDelFlag);
        // 所有人有权限看到 只是没有权限操作而已 暂定这样
        if (uid != 0) {
            List<Integer> menuIdList = roleMenuService.getMenuIdByUserId(uid);
            sysMenuLambdaQueryWrapper.in(SysMenu::getMenuId, menuIdList);
        }
        List<SysMenu> sysMenus = new ArrayList<>();
        List<SysMenu> menus = baseMapper.selectList(sysMenuLambdaQueryWrapper);
        menus.forEach(menu -> {
            if (menu.getParentId() == null || menu.getParentId() == 0) {
                menu.setLevel(0);
                if (AdminUtil.exists(sysMenus, menu)) {
                    sysMenus.add(menu);
                }
            }
        });
        sysMenus.sort(Comparator.comparing(SysMenu::getSort));
        AdminUtil.findChildren(sysMenus, menus, 0);
        return sysMenus;
    }
}
