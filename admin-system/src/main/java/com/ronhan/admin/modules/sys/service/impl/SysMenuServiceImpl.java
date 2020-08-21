package com.ronhan.admin.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ronhan.admin.common.constant.MenuTypeEnum;
import com.ronhan.admin.common.exception.BaseException;
import com.ronhan.admin.common.utils.R;
import com.ronhan.admin.modules.sys.domain.SysMenu;
import com.ronhan.admin.modules.sys.dto.MenuDTO;
import com.ronhan.admin.modules.sys.mapper.SysMenuMapper;
import com.ronhan.admin.modules.sys.service.ISysMenuService;
import com.ronhan.admin.modules.sys.service.ISysRoleMenuService;
import com.ronhan.admin.modules.sys.util.AdminUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public boolean updateMenuById(MenuDTO entity) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(entity, sysMenu);
        // 菜单校验
        verifyForm(sysMenu);
        return this.updateById(sysMenu);
    }
    /**
     * 验证菜单参数是否正确
     */
    private void verifyForm(SysMenu menu) {
        //上级菜单类型
        int parentType = MenuTypeEnum.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            SysMenu parentMenu = getMenuById(menu.getParentId());
            parentType = parentMenu.getType();
        }
        //目录、菜单
        if (menu.getType() == MenuTypeEnum.CATALOG.getValue() ||
                menu.getType() == MenuTypeEnum.MENU.getValue()) {
            if (parentType != MenuTypeEnum.CATALOG.getValue()) {
                throw new BaseException("上级菜单只能为目录类型");
            }
            return;
        }
        //按钮
        if (menu.getType() == MenuTypeEnum.BUTTON.getValue()) {
            if (parentType != MenuTypeEnum.MENU.getValue()) {
                throw new BaseException("上级菜单只能为菜单类型");
            }
        }
    }
    @Override
    public SysMenu getMenuById(Integer parentId) {
        return baseMapper.selectOne(Wrappers.<SysMenu>lambdaQuery()
                .select(SysMenu::getType).eq(SysMenu::getMenuId, parentId));
    }

    @Override
    public R removeMenuById(Serializable id) {
        List<Integer> idList =
                this.list(Wrappers.<SysMenu>query().lambda().eq(SysMenu::getParentId, id)).stream()
                        .map(SysMenu::getMenuId).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(idList)) {
            return R.error("菜单含有下级不能删除");
        }
        return R.ok(this.removeById(id));
    }

}
