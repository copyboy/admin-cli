package com.ronhan.admin.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ronhan.admin.modules.sys.domain.SysMenu;
import com.ronhan.admin.modules.sys.domain.SysRole;
import com.ronhan.admin.modules.sys.dto.RoleDTO;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 通过用户ID，查询角色信息
     */
    List<SysRole> findRolesByUserId(Integer userId);

    /**
     * 获取角色列表
     */
    List<SysRole> selectRoleList(String roleName);

    /**
     * 保存角色和菜单
     */
    boolean saveRoleMenu(RoleDTO roleDto);

    /**
     * 更新角色和菜单
     */
    boolean updateRoleMenu(RoleDTO roleDto);

    /**
     * 根据主键删除角色
     */
    @Override
    boolean removeById(Serializable id);

    /**
     * 根据角色id获取菜单
     */
    List<SysMenu> findMenuListByRoleId(int roleId);
}
