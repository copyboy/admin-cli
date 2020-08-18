package com.ronhan.admin.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ronhan.admin.modules.sys.domain.SysRole;

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
}
