package com.ronhan.admin.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ronhan.admin.modules.sys.domain.SysMenu;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 根据用户id查询权限
     **/
    List<String> findPermsByUserId(Integer userId);

    /**
     * 根据用户id查找菜单树
     */
    List<SysMenu> selectMenuTree(Integer uid);
}
