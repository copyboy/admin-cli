package com.ronhan.admin.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ronhan.admin.modules.sys.domain.SysRole;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 通过用户ID，查询角色信息
     */
    @Select("SELECT r.* FROM sys_role r, sys_user_role ur WHERE r.role_id = ur.role_id AND r.del_flag = 0 and ur.user_id = (#{userId})")
    List<SysRole> listRolesByUserId(Integer userId);
}
