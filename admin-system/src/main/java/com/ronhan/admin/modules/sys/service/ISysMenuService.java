package com.ronhan.admin.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ronhan.admin.common.utils.R;
import com.ronhan.admin.modules.sys.domain.SysMenu;
import com.ronhan.admin.modules.sys.dto.MenuDTO;

import java.io.Serializable;
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

    /**
     * 更新菜单信息
     */
    boolean updateMenuById(MenuDTO entity);

    /**
     * 删除菜单信息
     */
    R removeMenuById(Serializable id);

    /**
     * 根据父id查询菜单
     **/
    SysMenu getMenuById(Integer parentId);
}
