package com.ronhan.admin.modules.sys.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ronhan.admin.modules.data.strategy.DataScopeContext;
import com.ronhan.admin.modules.sys.domain.SysMenu;
import com.ronhan.admin.modules.sys.domain.SysRole;
import com.ronhan.admin.modules.sys.domain.SysRoleDept;
import com.ronhan.admin.modules.sys.domain.SysRoleMenu;
import com.ronhan.admin.modules.sys.dto.RoleDTO;
import com.ronhan.admin.modules.sys.mapper.SysRoleMapper;
import com.ronhan.admin.modules.sys.service.ISysRoleDeptService;
import com.ronhan.admin.modules.sys.service.ISysRoleMenuService;
import com.ronhan.admin.modules.sys.service.ISysRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Resource
    private ISysRoleDeptService roleDeptService;
    @Resource
    private DataScopeContext dataScopeContext;
    @Resource
    private ISysRoleMenuService roleMenuService;

    @Override
    public List<SysRole> findRolesByUserId(Integer userId) {
        return baseMapper.listRolesByUserId(userId);
    }

    @Override
    public List<SysRole> selectRoleList(String roleName) {
        LambdaQueryWrapper<SysRole> sysRoleLambdaQueryWrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotEmpty(roleName)) {
            sysRoleLambdaQueryWrapper.like(SysRole::getRoleName, roleName);
        }
        List<SysRole> sysRoles = baseMapper.selectList(sysRoleLambdaQueryWrapper);
        return sysRoles.stream().peek(sysRole ->
                sysRole.setRoleDeptList(
                        roleDeptService.getRoleDeptIds(sysRole.getRoleId()).stream().map(SysRoleDept::getDeptId)
                                .collect(Collectors.toList()))
        ).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveRoleMenu(RoleDTO roleDto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleDto, sysRole);
        // 根据数据权限范围查询部门ids
        List<Integer> ids = dataScopeContext.getDeptIdsForDataScope(roleDto, roleDto.getDsType());

        StringJoiner dsScope = new StringJoiner(",");
        ids.forEach(integer -> dsScope.add(Integer.toString(integer)));
        sysRole.setDsScope(dsScope.toString());
        baseMapper.insertRole(sysRole);
        Integer roleId = sysRole.getRoleId();
        //维护角色菜单
        List<SysRoleMenu> roleMenus = roleDto.getRoleMenus();
        if (CollectionUtil.isNotEmpty(roleMenus)) {
            List<SysRoleMenu> rms = roleMenus.stream().map(sysRoleMenu -> {
                SysRoleMenu roleMenu = new SysRoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(sysRoleMenu.getMenuId());
                return roleMenu;
            }).collect(Collectors.toList());
            roleMenuService.saveBatch(rms);
        }
        // 维护角色部门权限
        // 根据数据权限范围查询部门ids
        if (CollectionUtil.isNotEmpty(ids)) {
            List<SysRoleDept> roleDeptList = ids.stream().map(integer -> {
                SysRoleDept sysRoleDept = new SysRoleDept();
                sysRoleDept.setDeptId(integer);
                sysRoleDept.setRoleId(roleId);
                return sysRoleDept;
            }).collect(Collectors.toList());

            roleDeptService.saveBatch(roleDeptList);
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateRoleMenu(RoleDTO roleDto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleDto, sysRole);

        List<SysRoleMenu> roleMenus = roleDto.getRoleMenus();
        roleMenuService.remove(Wrappers.<SysRoleMenu>query().lambda().eq(SysRoleMenu::getRoleId, sysRole.getRoleId()));
        roleDeptService.remove(Wrappers.<SysRoleDept>query().lambda().eq(SysRoleDept::getRoleId, sysRole.getRoleId()));

        if (CollectionUtil.isNotEmpty(roleMenus)) {
            roleMenuService.saveBatch(roleMenus);
        }
        // 根据数据权限范围查询部门ids
        List<Integer> ids = dataScopeContext.getDeptIdsForDataScope(roleDto, roleDto.getDsType());

        StringJoiner dsScope = new StringJoiner(",");
        ids.forEach(integer -> dsScope.add(Integer.toString(integer)));
        if (CollectionUtil.isNotEmpty(ids)) {
            List<SysRoleDept> roleDeptList = ids.stream().map(integer -> {
                SysRoleDept sysRoleDept = new SysRoleDept();
                sysRoleDept.setDeptId(integer);
                sysRoleDept.setRoleId(roleDto.getRoleId());
                return sysRoleDept;
            }).collect(Collectors.toList());
            roleDeptService.saveBatch(roleDeptList);
        }
        sysRole.setDsScope(dsScope.toString());
        baseMapper.updateById(sysRole);
        return true;
    }

    @Override
    public List<SysMenu> findMenuListByRoleId(int roleId) {
        return baseMapper.findMenuListByRoleId(roleId);
    }
}
