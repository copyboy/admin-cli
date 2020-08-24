package com.ronhan.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ronhan.admin.modules.data.strategy.DataScopeContext;
import com.ronhan.admin.modules.data.tenant.TenantContextHolder;
import com.ronhan.admin.modules.sys.domain.*;
import com.ronhan.admin.modules.sys.mapper.SysTenantMapper;
import com.ronhan.admin.modules.sys.service.*;
import com.ronhan.admin.modules.sys.util.AdminUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * <p>
 * 租户表 服务实现类
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
@Service
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements ISysTenantService {

    @Resource
    private ISysUserRoleService userRoleService;
    @Resource
    private ISysDeptService deptService;
    @Resource
    private ISysMenuService menuService;
    @Resource
    private ISysUserService userService;
    @Resource
    private ISysRoleService roleService;
    @Resource
    private ISysRoleMenuService roleMenuService;
    @Resource
    private DataScopeContext dataScopeContext;

    /**
     * 一般租户授权时
     * 1.保存租户
     * 2.初始化权限相关表
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveTenant(SysTenant sysTenant) {
        this.save(sysTenant);
        // 修改租户Id 每次插入都是新的
        TenantContextHolder.setCurrentTenantId(Long.valueOf(sysTenant.getId()));
        // 插入部门
        SysDept dept = new SysDept();
        dept.setName("默认部门");
        dept.setParentId(0);
        dept.setSort(0);
        deptService.save(dept);
        // 构造初始化用户
        SysUser user = new SysUser();
        user.setUsername("root");
        // 默认密码
        user.setPassword(AdminUtil.encode("123456"));
        user.setDeptId(dept.getDeptId());
        userService.save(user);
        // 构造新角色
        SysRole role = new SysRole();
        role.setRoleCode("ROLE_ADMIN");
        role.setRoleName("默认角色");
        role.setDsType(1);
        // 默认全部权限
        List<Integer> ids = dataScopeContext.getDeptIdsForDataScope(null, 1);
        StringJoiner dsScope = new StringJoiner(",");
        ids.forEach(integer -> dsScope.add(Integer.toString(integer)));
        role.setDsScope(dsScope.toString());
        roleService.save(role);
        // 用户角色关系
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(user.getUserId());
        userRole.setRoleId(role.getRoleId());
        userRoleService.save(userRole);

        List<SysMenu> list = menuService.list();
        List<SysMenu> sysMenuList = new ArrayList<>();
        list.forEach(sysMenu -> {
            if (sysMenu.getType() == 1) {
                if ("代码生成".equals(sysMenu.getName())) {
                    List<SysMenu> sysMenus = treeMenuList(list, sysMenu.getMenuId());
                    sysMenus.add(sysMenu);
                    sysMenuList.addAll(sysMenus);
                }
                if ("租户管理".equals(sysMenu.getName())) {
                    List<SysMenu> sysMenus = treeMenuList(list, sysMenu.getMenuId());
                    sysMenus.add(sysMenu);
                    sysMenuList.addAll(sysMenus);
                }

            }
        });
        sysMenuList.forEach(sysMenu -> list.removeIf(next -> next.getMenuId().equals(sysMenu.getMenuId())));
        // 查询全部菜单,构造角色菜单关系
        List<SysRoleMenu> collect = list
                .stream().map(menu -> {
                    SysRoleMenu roleMenu = new SysRoleMenu();
                    roleMenu.setRoleId(role.getRoleId());
                    roleMenu.setMenuId(menu.getMenuId());
                    return roleMenu;
                }).collect(Collectors.toList());
        return roleMenuService.saveBatch(collect);
    }

    @Override
    public List<SysTenant> getNormalTenant() {
        return list(Wrappers.<SysTenant>lambdaQuery()
                // 状态为0的
                .eq(SysTenant::getStatus, 0)
                // 开始时间小于等于现在的时间
                .le(SysTenant::getStartTime, LocalDateTime.now())
                // 结束时间大于等于现在的时间
                .ge(SysTenant::getEndTime, LocalDateTime.now()));
    }

    private List<SysMenu> treeMenuList(List<SysMenu> menuList, int pid) {
        List<SysMenu> childMenu = new ArrayList<>();
        for (SysMenu mu : menuList) {
            //遍历出父id等于参数的id，add进子节点集合
            if (mu.getParentId() == pid) {
                //递归遍历下一级
                treeMenuList(menuList, mu.getMenuId());
                childMenu.add(mu);
            }
        }
        return childMenu;
    }
}
