package com.ronhan.admin.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ronhan.admin.modules.sys.domain.SysRoleMenu;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色菜单表 Mapper 接口
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {


    /**
     * 根据userId获取菜单id
     **/
    @Select("SELECT rm.menu_id FROM sys_role_menu rm,sys_user_role ur,sys_user u " +
            "WHERE u.user_id = #{userId} AND u.user_id = ur.user_id AND rm.role_id = ur.role_id")
    List<Integer> getMenuIdByUserId(Integer userId);
}
