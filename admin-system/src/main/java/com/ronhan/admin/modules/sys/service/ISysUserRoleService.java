package com.ronhan.admin.modules.sys.service;

import com.ronhan.admin.modules.sys.domain.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
     * 根据用户id查询用户角色关系
     */
    List<SysUserRole> selectUserRoleListByUserId(Integer userId);
}
