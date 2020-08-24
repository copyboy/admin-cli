package com.ronhan.admin.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ronhan.admin.modules.sys.domain.SysMenu;
import com.ronhan.admin.modules.sys.domain.SysRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
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

    /**
     * 插入用户权限
     */
    @Insert("insert into sys_role (role_name,role_code,role_desc,ds_type,ds_scope) values (#{roleName}, #{roleCode},#{roleDesc},#{dsType},#{dsScope})")
    @Options(useGeneratedKeys=true, keyProperty="roleId", keyColumn="role_id")
    Boolean insertRole(SysRole sysRole);

    /**
     * 根据角色ID查询菜单权限
     */
    @Select("select m.menu_id,m.name,m.type,m.parent_id,m.sort,m.perms from sys_menu m, sys_role_menu rm where rm.role_id = #{roleId} and m.menu_id = rm.menu_id")
    List<SysMenu> findMenuListByRoleId(int roleId);
}
