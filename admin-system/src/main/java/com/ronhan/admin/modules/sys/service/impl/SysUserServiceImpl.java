package com.ronhan.admin.modules.sys.service.impl;

import com.ronhan.admin.modules.sys.domain.SysUser;
import com.ronhan.admin.modules.sys.mapper.SysUserMapper;
import com.ronhan.admin.modules.sys.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}