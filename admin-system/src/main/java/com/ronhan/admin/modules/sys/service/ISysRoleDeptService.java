package com.ronhan.admin.modules.sys.service;

import com.ronhan.admin.modules.sys.domain.SysRoleDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 角色与部门对应关系 服务类
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
public interface ISysRoleDeptService extends IService<SysRoleDept> {


    /**
     * 根据角色id查询部门ids
     */
    List<SysRoleDept> getRoleDeptIds(int roleId);
}
