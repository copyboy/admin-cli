package com.ronhan.admin.modules.sys.service.impl;

import com.ronhan.admin.modules.sys.domain.SysRoleMenu;
import com.ronhan.admin.modules.sys.mapper.SysRoleMenuMapper;
import com.ronhan.admin.modules.sys.service.ISysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色菜单表 服务实现类
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    @Override
    public List<Integer> getMenuIdByUserId(Integer userId) {
        return baseMapper.getMenuIdByUserId(userId);
    }
}
